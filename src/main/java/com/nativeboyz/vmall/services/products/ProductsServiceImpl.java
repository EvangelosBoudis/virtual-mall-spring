package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.ActionType;
import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.ImageAction;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
    public Page<ProductDto> findProducts(QueryCriteria criteria, UUID customerId) {

        // Criteria
        Pageable pageable = criteria.getPageable();
        UUID categoryId = criteria.getCategoryId();
        String textMatch = criteria.getTextMatch();

        logger.info("page: " + pageable.toString() + " category: " + categoryId + " text: " + textMatch);

        Page<ProductEntity> entities;

        if (categoryId != null) {
            entities = productsRepository.findByCategoryId(categoryId, pageable);
        } else if (textMatch != null) {
            entities = productsRepository.findByTextMatch(textMatch.toLowerCase(), pageable);
        } else {
            entities = productsRepository.findAll(pageable);
        }

        Page<ProductDto> products = entities.map(ProductDto::new);

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
    @Transactional
    public ProductInfoDto saveProduct(ProductCriteria criteria) {

        CustomerEntity customerEntity = customersRepository
                .findById(criteria.getOwnerId())
                .orElseThrow();

        List<CategoryEntity> categoryEntities = categoriesRepository.findAllById(criteria.getCategories());

        ProductEntity productEntity = new ProductEntity(
                criteria.getName(),
                criteria.getPrice()
        );

        ProductInfoEntity productInfoEntity = new ProductInfoEntity(
                criteria.getDescription(),
                criteria.getDetails(),
                criteria.getHashTags().toArray(String[]::new)
        );

        productEntity.setCustomerEntity(customerEntity);
        productEntity.setProductInfoEntity(productInfoEntity);
        productEntity.setCategoryEntities(new HashSet<>(categoryEntities));

        Set<ProductImageEntity> imageEntities = criteria
                .getImageActions()
                .stream()
                .map(ProductImageEntity::new)
                .collect(Collectors.toSet());

        productEntity.setProductImageEntities(imageEntities);

        ProductInfoDto infoDto = new ProductInfoDto(productsRepository.save(productEntity));
        applyAdditionalInfo(infoDto, infoDto.getOwnerId());
        return infoDto;
    }

    @Override
    @Transactional
    public ProductInfoDto updateProduct(UUID productId, ProductCriteria criteria) {

        ProductEntity productEntity = productsRepository
                .findById(productId)
                .orElseThrow();

        productEntity.setName(criteria.getName());
        productEntity.setPrice(criteria.getPrice());

        ProductInfoEntity infoEntity = productEntity.getProductInfoEntity();

        infoEntity.setDetails(criteria.getDetails());
        infoEntity.setDescription(criteria.getDescription());
        infoEntity.setHashTags(criteria.getHashTags().toArray(String[]::new));

        List<CategoryEntity> categoryEntities = categoriesRepository.findAllById(criteria.getCategories());
        productEntity.setCategoryEntities(new HashSet<>(categoryEntities));

        // replace image entities with new one

        // #1 delete existing image entities
        Set<ProductImageEntity> imageEntities = productEntity.getProductImageEntities();
        productImagesRepository.deleteInBatch(imageEntities);

        // #2 apply new image entities into product entity
        Set<ProductImageEntity> newImageEntities = ImageAction
                .filter(criteria.getImageActions(), new ActionType[] { ActionType.SAVE, ActionType.UPDATE })
                .stream()
                .map(ProductImageEntity::new)
                .collect(Collectors.toSet());

        productEntity.setProductImageEntities(newImageEntities);

        // #3 save product entity
        ProductInfoDto infoDto = new ProductInfoDto(productsRepository.save(productEntity));
        applyAdditionalInfo(infoDto, infoDto.getOwnerId());
        return infoDto;
    }

    @Override
    public void deleteProduct(UUID id) {
        productsRepository.deleteById(id);
    }

    @Override
    public List<String> findProductImageNames(UUID productId) {
        return productImagesRepository
                .findByProductId(productId)
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList());
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