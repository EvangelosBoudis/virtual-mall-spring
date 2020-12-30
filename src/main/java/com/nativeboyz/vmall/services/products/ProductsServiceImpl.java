package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.criteria.FavoriteCriteria;
import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
import com.nativeboyz.vmall.models.criteria.RateCriteria;
import com.nativeboyz.vmall.models.dto.*;
import com.nativeboyz.vmall.models.entities.*;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import com.nativeboyz.vmall.repositories.categories.CategoriesRepository;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import com.nativeboyz.vmall.repositories.favorites.FavoritesRepository;
import com.nativeboyz.vmall.repositories.productImages.ProductImagesRepository;
import com.nativeboyz.vmall.repositories.products.ProductsRepository;
import com.nativeboyz.vmall.repositories.rates.RatesRepository;
import com.nativeboyz.vmall.repositories.views.ViewsRepository;
import com.nativeboyz.vmall.specifications.FavoriteSpecification;
import com.nativeboyz.vmall.specifications.ViewsSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Transactional
    public ProductDto findProduct(UUID productId, UUID requesterId) {
        ProductEntity productEntity = productsRepository.findById(productId).orElseThrow();
        saveView(requesterId, productId);
        ProductDto productDto = new ProductDto(productEntity);
        productDto.setInfo(getProductAdditionalInfo(productId, requesterId));
        return productDto;
    }

    @Override
    public ProductDetailsDto findProductDetails(UUID productId, UUID requesterId) {
        ProductEntity productEntity = productsRepository.findById(productId).orElseThrow();
        ProductDetailsDto productDetailsDto = new ProductDetailsDto(productEntity);
        productDetailsDto.setInfo(getProductAdditionalInfo(productId, requesterId));
        return productDetailsDto;
    }

    @Override
    public Page<ProductDto> findProducts(UUID requesterId, QueryCriteria criteria) {

        // Order by -> product properties + viewsQty, favoritesQty, avgRate

        UUID categoryId = criteria.getCategoryId();
        String searchKey = (criteria.getSearchKey() != null) ? criteria.getSearchKey().toLowerCase() : "";

        Page<ProductEntity> productEntities = (categoryId == null) ?
                productsRepository.findAllBySearchKey(searchKey, criteria.getPageable()) :
                productsRepository.findAllByCategoryIdAndSearchKey(categoryId, searchKey, criteria.getPageable());

        return productEntitiesToDTOs(productEntities);
    }

    @Override
    public Page<ProductDto> findCustomerProducts(UUID customerId, QueryCriteria criteria) {
        Page<ProductEntity> productEntities = productsRepository.findAllByCustomerId(customerId, criteria.getPageable());
        return productEntitiesToDTOs(productEntities);
    }

    @Override
    public Page<ProductDto> findCustomerFavoriteProducts(UUID customerId, QueryCriteria criteria) {

        Page<UUID> ids = favoritesRepository
                .findAll(
                        new FavoriteSpecification(customerId, criteria.getSearchKey()),
                        criteria.getPageable()
                )
                .map(f -> f.getId().getProductId());

        Stream<ProductEntity> productEntities = productsRepository
                .findAllById(ids.stream().collect(Collectors.toList()))
                .stream();

        Page<ProductEntity> transformed = ids.map(id -> productEntities.filter(p -> p.getId() == id).findAny().orElseThrow());

        return productEntitiesToDTOs(transformed);
    }

    @Override
    public Page<ProductDto> findCustomerViewedProducts(UUID customerId, QueryCriteria criteria) {

        Page<UUID> ids = viewsRepository
                .findAll(
                        new ViewsSpecification(customerId, criteria.getSearchKey()),
                        criteria.getPageable()
                )
                .map(v -> v.getId().getProductId());

        Stream<ProductEntity> productEntities = productsRepository
                .findAllById(ids.stream().collect(Collectors.toList()))
                .stream();

        Page<ProductEntity> transformed = ids.map(id -> productEntities.filter(p -> p.getId() == id).findAny().orElseThrow());

        return productEntitiesToDTOs(transformed);
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
                getProductAdditionalInfo(productDetailsDto.getId(), productDetailsDto.getOwnerId())
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
                getProductAdditionalInfo(productDetailsDto.getId(), productDetailsDto.getOwnerId())
        );
        return productDetailsDto;
    }

    @Override
    public void deleteProduct(UUID productId) {
        productsRepository.deleteById(productId);
    }

    @Override
    public void saveView(UUID customerId, UUID productId) {

        productExistsOrElseThrow(productId);
        customerExistsOrElseThrow(customerId);

        viewsRepository.save(
                new ViewEntity(new CustomerProductIdentity(customerId, productId))
        );
    }

    @Override
    public FavoriteEntity saveFavorite(FavoriteCriteria criteria) {

        productExistsOrElseThrow(criteria.getProductId());
        customerExistsOrElseThrow(criteria.getCustomerId());

        return favoritesRepository.save(
                new FavoriteEntity(
                        new CustomerProductIdentity(criteria.getCustomerId(), criteria.getProductId()),
                        criteria.getStatus()
                )
        );
    }

    @Override
    public RateEntity saveRate(RateCriteria criteria) {

        productExistsOrElseThrow(criteria.getProductId());
        customerExistsOrElseThrow(criteria.getCustomerId());

        return ratesRepository.save(
                new RateEntity(
                        new CustomerProductIdentity(criteria.getCustomerId(), criteria.getProductId()),
                        criteria.getRate(),
                        criteria.getComment()
                )
        );
    }

    private void saveSearch(String searchKey) {
        // TODO: Implement
    }

    private void customerExistsOrElseThrow(UUID customerId) {
        if (!customersRepository.existsById(customerId))
            throw new EmptyResultDataAccessException("No such customer: " + customerId, 1);
    }

    private void productExistsOrElseThrow(UUID productId) {
        if (!productsRepository.existsById(productId))
            throw new EmptyResultDataAccessException("No such product: " + productId, 1);
    }

    private Page<ProductDto> productEntitiesToDTOs(Page<ProductEntity> productEntities) {

        List<UUID> ids = productEntities
                .map(ProductEntity::getId)
                .stream()
                .collect(Collectors.toList());

        Map<UUID, Integer> vMap = calculateCount(viewsRepository.findAllByProductId(ids));
        Map<UUID, Integer> fMap = calculateCount(favoritesRepository.findAllByProductId(ids));
        Map<UUID, Float> rMap = calculateAvgRate(ratesRepository.findAllByProductId(ids));

        return productEntities.map(productEntity -> {

            ProductDto productDto = new ProductDto(productEntity);
            Integer views = vMap.get(productEntity.getId());
            Integer favorites = fMap.get(productEntity.getId());

            productDto.setInfo(
                    new ProductAdditionalInfo(
                            views != null ? views : 0,
                            favorites != null ? favorites : 0,
                            rMap.get(productEntity.getId()),
                            null
                    )
            );

            return productDto;
        });
    }

    private ProductAdditionalInfo getProductAdditionalInfo(
            UUID productId,
            UUID requesterId
    ) {
        return new ProductAdditionalInfo(
                viewsRepository.countByProductId(productId),
                favoritesRepository.countByProductId(productId),
                ratesRepository.findProductAvgRate(productId),
                favoritesRepository.isFavoriteByCustomer(productId, requesterId) > 0
        );
    }

    private <T extends CustomerProductEntity> Map<UUID, Integer> calculateCount(List<T> entities) {

        Map<UUID, Integer> map = new HashMap<>();

        entities.forEach(entity -> {
            UUID id = entity.getId().getProductId();
            int count = (map.get(id) != null) ? map.get(id) : 0;
            map.put(id, count + 1);
        });

        return map;
    }

    private Map<UUID, Float> calculateAvgRate(List<RateEntity> rateEntities) {

        Map<UUID, Integer> cMap = calculateCount(rateEntities);
        Map<UUID, Float> rMap = new HashMap<>();

        rateEntities.forEach(rateEntity -> {
            UUID id = rateEntity.getId().getProductId();
            float rate = (rMap.get(id) != null) ? rMap.get(id) : 0;
            rMap.put(id, rate + rateEntity.getRate());
        });

        return new HashMap<>() {{
            for (UUID key : rMap.keySet()) {
                Float rates = rMap.get(key);
                Integer views = (cMap.get(key) != null && cMap.get(key) > 0) ? cMap.get(key) : 1;
                put(key, rates / views);
            }
        }};
    }

}