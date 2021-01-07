package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

        ProductEntity product = productsRepository
                .findById(productId)
                .orElseThrow();

        saveView(requesterId, productId);

        return new ProductDto(
                product,
                findProductAdditionalInfo(productId, requesterId)
        );
    }

    @Override
    public ProductDetailsDto findProductDetails(UUID productId, UUID requesterId) {

        ProductEntity product = productsRepository
                .findById(productId)
                .orElseThrow();

        saveView(requesterId, productId);

        return new ProductDetailsDto(
                product,
                findProductAdditionalInfo(productId, requesterId)
        );
    }

    @Override
    public Page<ProductDto> findProducts(UUID requesterId, QueryCriteria criteria) {

        // Order by -> product properties + viewsQty, favoritesQty, avgRate

        UUID categoryId = criteria.getCategoryId();
        String searchKey = (criteria.getSearchKey() != null) ? criteria.getSearchKey().toLowerCase() : null;

        Page<ProductEntity> products;

        if (categoryId != null && searchKey != null) {
            products = productsRepository.findAllByCategoryIdAndSearchKey(categoryId, searchKey, criteria.getPageable());
        } else if (categoryId != null) {
            products = productsRepository.findAllByCategoryId(categoryId, criteria.getPageable());
        } else {
            products = productsRepository.findAllBySearchKey(searchKey != null ? searchKey : "", criteria.getPageable());
        }

        return convertProductToDto(products);
    }

    @Override
    public Page<ProductDto> findCustomerProducts(UUID customerId, QueryCriteria criteria) {

        Page<ProductEntity> products = productsRepository.findAllByCustomerId(
                customerId,
                criteria.getPageable()
        );

        return convertProductToDto(products);
    }

    @Override
    public Page<ProductDto> findCustomerFavoriteProducts(UUID customerId, QueryCriteria criteria) {

        Page<FavoriteEntity> favorites = favoritesRepository.findAllByCustomerId(
                customerId,
                criteria.getSearchKey(),
                criteria.getPageable()
        );

        return convertCPtoDto(favorites);
    }

    @Override
    public Page<ProductDto> findCustomerViewedProducts(UUID customerId, QueryCriteria criteria) {

        Page<ViewEntity> views = viewsRepository.findAllByCustomerId(
                customerId,
                criteria.getSearchKey(),
                criteria.getPageable()
        );

        return convertCPtoDto(views);
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

        CustomerEntity customer = customersRepository
                .findById(criteria.getOwnerId())
                .orElseThrow();

        List<CategoryEntity> categories = categoriesRepository.findAllById(criteria.getCategories());

        ProductEntity product = new ProductEntity(
                criteria.getTitle(),
                criteria.getPrice()
        );

        ProductDetailsEntity productDetails = new ProductDetailsEntity(
                criteria.getDescription(),
                criteria.getCharacteristics(),
                criteria.getKeywords()
        );

        product.setDetailsEntity(productDetails);
        product.setCustomerEntity(customer);
        product.setCategoryEntities(new HashSet<>(categories));

        Set<ProductImageEntity> images = criteria
                .getOrderedImages()
                .stream()
                .map(ProductImageEntity::new)
                .collect(Collectors.toSet());

        product.setImageEntities(images);

        ProductEntity savedProduct = productsRepository.save(product);

        return new ProductDetailsDto(
                savedProduct,
                findProductAdditionalInfo(savedProduct.getId(), criteria.getOwnerId())
        );
    }

    @Override
    @Transactional
    public ProductDetailsDto updateProduct(UUID productId, ProductCriteria criteria) {

        ProductEntity product = productsRepository
                .findById(productId)
                .orElseThrow();

        product.setTitle(criteria.getTitle());
        product.setPrice(criteria.getPrice());

        ProductDetailsEntity productDetails = product.getDetailsEntity();

        productDetails.setCharacteristics(criteria.getCharacteristics());
        productDetails.setDescription(criteria.getDescription());
        productDetails.setKeywords(criteria.getKeywords());

        List<CategoryEntity> categoryEntities = categoriesRepository.findAllById(criteria.getCategories());
        product.setCategoryEntities(new HashSet<>(categoryEntities));

        // replace image entities with new one

        // #1 delete existing image entities
        List<String> imageNames = criteria
                .getOrderedImages()
                .stream()
                .map(OrderedImage::getName)
                .collect(Collectors.toList());

        List<ProductImageEntity> oldProductImageEntities = product
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

        product.setImageEntities(imageEntities);

        // #3 save product entity

        ProductEntity updatedProduct = productsRepository.save(product);

        return new ProductDetailsDto(
                updatedProduct,
                findProductAdditionalInfo(updatedProduct.getId(), criteria.getOwnerId())
        );
    }

    @Override
    public void deleteProduct(UUID productId) {
        productsRepository.deleteById(productId);
    }

    @Override
    public void saveView(UUID customerId, UUID productId) {

        boolean exists = customersRepository.existsById(customerId) && productsRepository.existsById(productId);

        if (exists)
            viewsRepository.save(
                    new ViewEntity(new CustomerProductIdentity(customerId, productId))
            );
    }

    private ProductAdditionalInfo findProductAdditionalInfo(
            UUID productId,
            UUID requesterId
    ) {

        boolean requesterFavorite = favoritesRepository.findCountByProductIdAndCustomerId(productId, requesterId) > 0;

        return new ProductAdditionalInfo(
                viewsRepository.findCountByProductId(productId),
                favoritesRepository.findCountByProductId(productId),
                ratesRepository.findAvgRateByProductId(productId),
                requesterFavorite
        );
    }

    private Page<ProductDto> convertProductToDto(Page<ProductEntity> products) {

        List<UUID> productIds = products
                .map(ProductEntity::getId)
                .stream()
                .collect(Collectors.toList());

        List<CountDto> viewsDto = viewsRepository.findAllCountDtoByProductIds(productIds);
        List<CountDto> favoritesDto = favoritesRepository.findAllCountDtoByProductIds(productIds);
        List<RateDto> ratesDto = ratesRepository.findAllRateDtoByProductIds(productIds);

        return products.map(product -> {

            Optional<CountDto> views = viewsDto
                    .stream().filter(v -> v.getProductId().equals(product.getId())).findFirst();

            Optional<CountDto> favorites = favoritesDto
                    .stream().filter(v -> v.getProductId().equals(product.getId())).findFirst();

            Optional<RateDto> rates = ratesDto
                    .stream().filter(r -> r.getProductId().equals(product.getId())).findFirst();

            return new ProductDto(
                    product,
                    new ProductAdditionalInfo(
                            views.map(CountDto::getCount).orElse(0L),
                            favorites.map(CountDto::getCount).orElse(0L),
                            rates.map(RateDto::getAvg).orElse((double) -1),
                            null
                    ));
        });

    }

    private <T extends CustomerProductEntity> Page<ProductEntity> convertCPtoProduct(Page<T> page) {

        List<UUID> productIds = page
                .map(cp -> cp.getId().getProductId())
                .stream()
                .collect(Collectors.toList());

        List<ProductEntity> products = productsRepository.findAllById(productIds);

        return page.map(cp ->
                products.stream()
                        .filter(p -> p.getId().equals(cp.getId().getProductId()))
                        .findAny()
                        .orElseThrow()
        );
    }

    private <T extends CustomerProductEntity> Page<ProductDto> convertCPtoDto(Page<T> page) {
        return convertProductToDto(convertCPtoProduct(page));
    }

}