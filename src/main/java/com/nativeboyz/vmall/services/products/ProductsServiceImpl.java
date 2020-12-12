package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
import com.nativeboyz.vmall.models.dto.OrderedImage;
import com.nativeboyz.vmall.models.dto.ProductAdditionalInfo;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.dto.ProductDetailsDto;
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
    public ProductDetailsDto findProductInfo(UUID productId, UUID customerId) {
        ProductEntity entity = productsRepository.findById(productId).orElseThrow();
        return applyAdditionalInfo(new ProductDetailsDto(entity), customerId);
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
            ProductAdditionalInfo additionalInfo = new ProductAdditionalInfo(
                    viewsQty != null ? viewsQty : 0,
                    favoritesQty != null ? favoritesQty : 0,
                    rateMap.get(product.getId()),
                    null
            );
            product.setAdditionalInfo(additionalInfo);
        });

        return products;
    }

    @Override
    @Transactional
    public ProductDetailsDto saveProduct(ProductCriteria criteria) {

        CustomerEntity customerEntity = customersRepository
                .findById(criteria.getOwnerId())
                .orElseThrow();

        List<CategoryEntity> categoryEntities = categoriesRepository.findAllById(criteria.getCategories());

        ProductEntity productEntity = new ProductEntity(
                criteria.getTitle(),
                criteria.getPrice()
        );

        ProductDetailsEntity detailsEntity = new ProductDetailsEntity(
                criteria.getDescription(),
                criteria.getCharacteristics(),
                criteria.getKeywords()
        );

        productEntity.setDetailsEntity(detailsEntity);
        productEntity.setCustomerEntity(customerEntity);
        productEntity.setCategoryEntities(new HashSet<>(categoryEntities));

        Set<ProductImageEntity> imageEntities = criteria
                .getOrderedImages()
                .stream()
                .map(ProductImageEntity::new)
                .collect(Collectors.toSet());

        productEntity.setImageEntities(imageEntities);

        ProductDetailsDto infoDto = new ProductDetailsDto(productsRepository.save(productEntity));
        applyAdditionalInfo(infoDto, infoDto.getOwnerId());
        return infoDto;
    }

    @Override
    @Transactional
    public ProductDetailsDto updateProduct(UUID id, ProductCriteria criteria) {

        ProductEntity productEntity = productsRepository
                .findById(id)
                .orElseThrow();

        productEntity.setTitle(criteria.getTitle());
        productEntity.setPrice(criteria.getPrice());

        ProductDetailsEntity detailsEntity = productEntity.getDetailsEntity();

        detailsEntity.setCharacteristics(criteria.getCharacteristics());
        detailsEntity.setDescription(criteria.getDescription());
        detailsEntity.setKeywords(criteria.getKeywords());

        List<CategoryEntity> categoryEntities = categoriesRepository.findAllById(criteria.getCategories());
        productEntity.setCategoryEntities(new HashSet<>(categoryEntities));

        // replace image entities with new one

        // #1 delete existing image entities
        List<String> imageNames = criteria
                .getOrderedImages()
                .stream()
                .map(OrderedImage::getName)
                .collect(Collectors.toList());

        List<ProductImageEntity> oldProductImageEntities = productEntity
                .getImageEntities()
                .stream()
                .filter(x -> !imageNames.contains(x.getImageName()))
                .collect(Collectors.toList());

        productImagesRepository.deleteInBatch(oldProductImageEntities);

        // #2 apply new image entities into product entity
        Set<ProductImageEntity> imageEntities = criteria
                .getOrderedImages()
                .stream()
                .map(ProductImageEntity::new)
                .collect(Collectors.toSet());

        productEntity.setImageEntities(imageEntities);

        // #3 save product entity
        ProductDetailsDto detailsDto = new ProductDetailsDto(productsRepository.save(productEntity));
        applyAdditionalInfo(detailsDto, detailsDto.getOwnerId());
        return detailsDto;
    }

    @Override
    public void deleteProduct(UUID id) {
        productsRepository.deleteById(id);
    }

    @Override
    public List<String> findProductImageNames(UUID id) {
        return productImagesRepository
                .findByProductId(id)
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList());
    }

    private <T extends ProductDto> T applyAdditionalInfo(T dto, UUID customerId) {
        UUID id = dto.getId();
        ProductAdditionalInfo additionalInfo = new ProductAdditionalInfo(
                viewsRepository.countByProductId(id),
                favoritesRepository.countByProductId(id),
                ratesRepository.avgRateByProductId(id),
                (favoritesRepository.isFavoriteByCustomer(id, customerId) > 0)
        );
        dto.setAdditionalInfo(additionalInfo);
        return  dto;
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