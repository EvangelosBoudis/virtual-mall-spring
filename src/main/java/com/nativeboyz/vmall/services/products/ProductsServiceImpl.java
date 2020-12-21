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
import com.nativeboyz.vmall.specifications.FavoriteSpecification;
import com.nativeboyz.vmall.specifications.ProductSpecification;
import com.nativeboyz.vmall.specifications.ViewsSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            ProductImagesRepository productImagesRepository
    ) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.customersRepository = customersRepository;
        this.viewsRepository = viewsRepository;
        this.ratesRepository = ratesRepository;
        this.favoritesRepository = favoritesRepository;
        this.productImagesRepository = productImagesRepository;
    }

    @Override
    public ProductDto findProduct(UUID productId, UUID requesterId) {
        ProductEntity productEntity = productsRepository.findById(productId).orElseThrow();
        ProductDto productDto = new ProductDto(productEntity);
        productDto.setInfo(getAdditionalInfo(productId, requesterId));
        return productDto;
    }

    @Override
    public ProductDetailsDto findProductDetails(UUID productId, UUID requesterId) {
        ProductEntity productEntity = productsRepository.findById(productId).orElseThrow();
        ProductDetailsDto productDetailsDto = new ProductDetailsDto(productEntity);
        productDetailsDto.setInfo(getAdditionalInfo(productId, requesterId));
        return productDetailsDto;
    }

    @Override
    public Page<ProductDto> findProducts(UUID requesterId, QueryCriteria criteria) {
        Page<ProductEntity> productEntities = productsRepository.findAll(
                new ProductSpecification(criteria.getCategoryId(), criteria.getSearchKey()),
                criteria.getPageable()
        );
        return entityToDto(productEntities);
    }

    @Override
    public Page<ProductDto> findCustomerProducts(UUID customerId, QueryCriteria criteria) {
        return entityToDto(
                productsRepository.findAllByCustomerId(customerId, criteria.getPageable())
        );
    }

    @Override
    public Page<ProductDto> findCustomerFavoriteProducts(UUID customerId, QueryCriteria criteria) {
        // by default order by "actionTimestamp"
        if (criteria.getSort() == null) criteria.setSort("actionTimestamp");

        Page<UUID> productIds = favoritesRepository
                .findAll(new FavoriteSpecification(customerId, criteria.getSearchKey()), criteria.getPageable())
                .map(f -> f.getId().getProductId());

        Stream<ProductEntity> productEntities = productsRepository
                .findAllById(productIds.stream().collect(Collectors.toList()))
                .stream();

        Page<ProductEntity> transformed = productIds.map(id -> productEntities.filter(p -> p.getId() == id).findAny().orElseThrow());
        return entityToDto(transformed);
    }

    @Override
    public Page<ProductDto> findCustomerViewedProducts(UUID customerId, QueryCriteria criteria) {

        // by default order by "actionTimestamp"
        if (criteria.getSort() == null) criteria.setSort("actionTimestamp");

        viewsRepository.findAll(
                new ViewsSpecification(customerId, criteria.getSearchKey()),
                criteria.getPageable()
        );

        // TODO: Replace with Specification Api
        return entityToDto(
                productsRepository.findAllCustomerViews(customerId, criteria.getPageable())
        );
    }

    @Override
    public List<String> findProductImageNames(UUID productId) {
        return productImagesRepository
                .findAllByProductId(productId)
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList());
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

        ProductDetailsDto productDetailsDto = new ProductDetailsDto(productsRepository.save(productEntity));
        productDetailsDto.setInfo(
                getAdditionalInfo(productDetailsDto.getId(), productDetailsDto.getOwnerId())
        );
        return productDetailsDto;
    }

    @Override
    @Transactional
    public ProductDetailsDto updateProduct(UUID productId, ProductCriteria criteria) {

        ProductEntity productEntity = productsRepository
                .findById(productId)
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
        ProductDetailsDto productDetailsDto = new ProductDetailsDto(productsRepository.save(productEntity));
        productDetailsDto.setInfo(
                getAdditionalInfo(productDetailsDto.getId(), productDetailsDto.getOwnerId())
        );
        return productDetailsDto;
    }

    @Override
    public void deleteProduct(UUID productId) {
        productsRepository.deleteById(productId);
    }

    private Page<ProductDto> entityToDto(Page<ProductEntity> products) {

        List<UUID> ids = products
                .map(ProductEntity::getId)
                .stream()
                .collect(Collectors.toList());

        Map<UUID, Integer> viewsMap = mapCount(viewsRepository.findAllByProductId(ids));
        Map<UUID, Integer> favoritesMap = mapCount(favoritesRepository.findAllByProductId(ids));
        Map<UUID, Float> ratesMap = mapAvgRate(ratesRepository.findAllByProductId(ids));

        return products.map(product -> {
            ProductDto dto = new ProductDto(product);
            Integer views = viewsMap.get(product.getId());
            Integer favorites = favoritesMap.get(product.getId());
            ProductAdditionalInfo info = new ProductAdditionalInfo(
                    views != null ? views : 0,
                    favorites != null ? favorites : 0,
                    ratesMap.get(product.getId()),
                    null
            );
            dto.setInfo(info);
            return dto;
        });
    }

    private ProductAdditionalInfo getAdditionalInfo(UUID productId, UUID requesterId) {
        int views = viewsRepository.countByProductId(productId);
        int favorites = favoritesRepository.countByProductId(productId);
        Float rates = ratesRepository.findProductAvgRate(productId);
        boolean customerFavorite = favoritesRepository.isFavoriteByCustomer(productId, requesterId) > 0;
        return new ProductAdditionalInfo(views, favorites, rates, customerFavorite);
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

    private Map<UUID, Float> mapAvgRate(List<RateEntity> rates) {
        Map<UUID, Integer> countMap = mapCount(rates);
        Map<UUID, Float> sumRateMap = new HashMap<>();

        rates.forEach(rate -> {
            UUID id = rate.getId().getProductId();
            float preRate = (sumRateMap.get(id) != null) ? sumRateMap.get(id) : 0;
            sumRateMap.put(id, preRate + rate.getRate());
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