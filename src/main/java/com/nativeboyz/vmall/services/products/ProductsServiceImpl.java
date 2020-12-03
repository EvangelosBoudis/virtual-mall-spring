package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.product.ProductTransformedCriteria;
import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.entities.ProductImageEntity;
import com.nativeboyz.vmall.repositories.categories.CategoriesRepository;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import com.nativeboyz.vmall.repositories.products.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final CustomersRepository customersRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository, CategoriesRepository categoriesRepository, CustomersRepository customersRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.customersRepository = customersRepository;
    }

    @Override
    public ProductEntity findProduct(UUID id) {
        return productsRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<ProductEntity> findProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ProductEntity saveProduct(ProductEntity entity) {
        return productsRepository.save(entity);
    }

    @Override
    @Transactional
    public ProductEntity saveProduct(ProductTransformedCriteria criteria) {

        CustomerEntity customerEntity = customersRepository
                .findById(criteria.getUploaderId())
                .orElseThrow();

        List<CategoryEntity> categoryEntities = categoriesRepository
                .findAllById(Arrays.asList(criteria.getCategories()));

        ProductEntity productEntity = new ProductEntity(
                criteria.getName(),
                criteria.getPrice(),
                criteria.getDescription(),
                criteria.getDetails(),
                criteria.getHashTags()
        );

        productEntity.setCustomerEntity(customerEntity);
        productEntity.setCategoryEntities(new HashSet<>(categoryEntities));

        Set<ProductImageEntity> imageEntities = new HashSet<>();
        for (int i = 0; i < criteria.getFileNames().length; i++) {
            imageEntities.add(new ProductImageEntity(
                    productEntity,
                    criteria.getFileNames()[i],
                    i));
        }

        productEntity.setProductImageEntities(imageEntities);

        return productsRepository.save(productEntity);

        /*ProductEntity savedProduct = productsRepository.save(productEntity);

        Set<ProductImageEntity> imageEntities = new HashSet<>();

        for (int i = 0; i < criteria.getFileNames().length; i++) {
            imageEntities.add(new ProductImageEntity(
                    savedProduct,
                    criteria.getFileNames()[i],
                    i));
        }

        savedProduct.setProductImageEntities(imageEntities);

        return savedProduct;*/
    }

    @Override
    public void deleteProduct(UUID id) {
        productsRepository.deleteById(id);
    }

}

/*        List<Category> categories = Arrays.stream(criteria.getCategories())
                .map(categoriesRepository::findById) // TODO: Replace with single query
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());*/