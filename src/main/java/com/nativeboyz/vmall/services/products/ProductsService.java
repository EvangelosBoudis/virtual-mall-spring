package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.dto.ProductDetailsDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductsService {

    ProductDto findProduct(UUID productId, UUID requesterId);

    ProductDetailsDto findProductDetails(UUID productId, UUID requesterId);

    Page<ProductDto> findProducts(UUID requesterId, QueryCriteria criteria);

    Page<ProductDto> findCustomerProducts(UUID customerId, QueryCriteria criteria);

    Page<ProductDto> findCustomerFavoriteProducts(UUID customerId, QueryCriteria criteria);

    Page<ProductDto> findCustomerViewedProducts(UUID customerId, QueryCriteria criteria);

    List<String> findProductImageNames(UUID productId);

    ProductDetailsDto saveProduct(ProductCriteria criteria);

    ProductDetailsDto updateProduct(UUID productId, ProductCriteria criteria);

    void deleteProduct(UUID productId);

    void saveView(UUID customerId, UUID productId);

}
