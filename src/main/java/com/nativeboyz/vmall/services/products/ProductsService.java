package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.dto.ProductDetailsDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductsService {

    ProductDto findProduct(UUID productId, UUID customerId);

    ProductDetailsDto findProductInfo(UUID productId, UUID customerId);

    Page<ProductDto> findProducts(QueryCriteria criteria, UUID customerId);

    ProductDetailsDto saveProduct(ProductCriteria criteria);

    ProductDetailsDto updateProduct(UUID id, ProductCriteria criteria);

    void deleteProduct(UUID id);

    List<String> findProductImageNames(UUID id);

}
