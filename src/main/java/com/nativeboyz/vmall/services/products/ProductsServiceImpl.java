package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.CustomerProductEntity;
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
    public ProductDto findProduct(UUID productId, UUID customerId) {
        ProductEntity entity = productsRepository.findById(productId).orElseThrow();
        return applyAdditionalInfo(new ProductDto(entity), customerId);
    }

    @Override
    public ProductInfoDto findProductInfo(UUID productId, UUID customerId) {
        ProductEntity entity = productsRepository.findById(productId).orElseThrow();
        return applyAdditionalInfo(new ProductInfoDto(entity), customerId);
    }

    @Override
    public Page<ProductDto> findProducts(Pageable pageable, UUID customerId) {
        Page<ProductDto> products = productsRepository
                .findAll(pageable)
                .map(ProductDto::new);

        List<UUID> ids = products
                .map(ProductDto::getId)
                .stream()
                .collect(Collectors.toList());

        Map<UUID, Integer> viewMap = mapCount(viewsRepository.findAllByProductId(ids));
        Map<UUID, Integer> favoriteMap = mapCount(favoritesRepository.findAllByProductId(ids));
        Map<UUID, Float> rateMap = mapAvgRate(ratesRepository.findAllByProductId(ids));

        products.forEach(product -> {
            Integer viewsQty = viewMap.get(product.getId());
            Integer favoritesQty = favoriteMap.get(product.getId());

            product.setViewsQty(viewsQty != null ? viewsQty : 0);
            product.setFavoritesQty(favoritesQty != null ? favoritesQty : 0);
            product.setAvgRate(rateMap.get(product.getId()));
        });

        return products;
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

    private <T extends ProductDto> T applyAdditionalInfo(T dto, UUID customerId) {
        UUID productId = dto.getId();
        dto.setViewsQty(viewsRepository.countByProductId(productId));
        dto.setFavoritesQty(favoritesRepository.countByProductId(productId));
        dto.setAvgRate(ratesRepository.avgRateByProductId(productId));
        dto.setFavorite((favoritesRepository.isFavoriteByCustomer(productId, customerId) > 0));
        return dto;
    }

    private <T extends CustomerProductEntity> Map<UUID, Integer> mapCount(List<T> entities) {
        Map<UUID, Integer> countMap = new HashMap<>();
        entities.forEach(e -> {
            UUID id = e.getId().getProductId();
            int preCount = (countMap.get(id) != null) ? countMap.get(id) : 0;
            countMap.put(id, preCount + 1);
        });
        return countMap;
    }

    private Map<UUID, Float> mapAvgRate(List<RateEntity> entities) {
        Map<UUID, Integer> countMap = mapCount(entities);
        Map<UUID, Float> sumRateMap = new HashMap<>();

        entities.forEach(e -> {
            UUID id = e.getId().getProductId();
            float preRate = (sumRateMap.get(id) != null) ? sumRateMap.get(id) : 0;
            sumRateMap.put(id, preRate + e.getRate());
        });

        return new HashMap<>() {{
            for (UUID key : sumRateMap.keySet()) {
                Float rates = sumRateMap.get(key);
                Integer views = (countMap.get(key) != null && countMap.get(key) > 0) ? countMap.get(key) : 1;
                put(key, rates / views);
            }
        }};
    }

}