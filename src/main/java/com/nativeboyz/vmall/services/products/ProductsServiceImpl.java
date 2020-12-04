package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.product.ProductTransformedCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.dto.ProductInfoDto;
import com.nativeboyz.vmall.models.entities.*;
import com.nativeboyz.vmall.repositories.categories.CategoriesRepository;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import com.nativeboyz.vmall.repositories.favorites.FavoritesRepository;
import com.nativeboyz.vmall.repositories.productImages.ProductImagesRepository;
import com.nativeboyz.vmall.repositories.products.ProductsRepository;
import com.nativeboyz.vmall.repositories.rates.RatesRepository;
import com.nativeboyz.vmall.repositories.views.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final CustomersRepository customersRepository;

    private final ViewsRepository viewsRepository;
    private final RatesRepository ratesRepository;
    private final FavoritesRepository favoritesRepository;

    private final ProductImagesRepository productImagesRepository;

    @Autowired
    public ProductsServiceImpl(
            ProductsRepository productsRepository,
            CategoriesRepository categoriesRepository,
            CustomersRepository customersRepository,
            ViewsRepository viewsRepository,
            RatesRepository ratesRepository,
            FavoritesRepository favoritesRepository,
            ProductImagesRepository productImagesRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.customersRepository = customersRepository;
        this.viewsRepository = viewsRepository;
        this.ratesRepository = ratesRepository;
        this.favoritesRepository = favoritesRepository;
        this.productImagesRepository = productImagesRepository;
    }

    @Override
    public List<String> findProductImages(UUID productId) {
        return productImagesRepository
                .findByProductId(productId)
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList());
    }

    @Override
    public ProductInfoDto findProduct(UUID productId, UUID customerId) {

        ProductEntity entity = productsRepository
                .findById(productId)
                .orElseThrow();

        ProductInfoDto dto = new ProductInfoDto(entity);

        dto.setViewsQty(viewsRepository.countByProductId(productId));
        dto.setFavoritesQty(favoritesRepository.countByProductId(productId));
        dto.setAvgRate(ratesRepository.averageRateByProductId(productId));
        dto.setFavorite((favoritesRepository.countByProductIdAndCustomerId(productId, customerId) > 0));

        return dto;
    }

    @Override
    public Page<ProductEntity> findProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public Page<ProductDto> findProducts(Pageable pageable, UUID customerId) {




        return null;
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
                criteria.getPrice()
        );

        ProductInfoEntity productInfoEntity = new ProductInfoEntity(
                criteria.getDescription(),
                criteria.getDetails(),
                criteria.getHashTags()
        );

        productEntity.setCustomerEntity(customerEntity);
        productEntity.setProductInfoEntity(productInfoEntity);
        productEntity.setCategoryEntities(new HashSet<>(categoryEntities));

        Set<ProductImageEntity> imageEntities = new HashSet<>();
        for (int i = 0; i < criteria.getFileNames().length; i++) {
            imageEntities.add(new ProductImageEntity(productEntity, criteria.getFileNames()[i], i));
        }
        productEntity.setProductImageEntities(imageEntities);

        return productsRepository.save(productEntity);
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