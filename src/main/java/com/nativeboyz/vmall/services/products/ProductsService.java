package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.product.ProductTransformedCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.dto.ProductInfoDto;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductsService {

    List<String> findProductImages(UUID productId);

    ProductInfoDto findProduct(UUID productId, UUID customerId);

    Page<ProductEntity> findProducts(Pageable pageable);

    Page<ProductDto> findProducts(Pageable pageable, UUID customerId);

    ProductEntity saveProduct(ProductEntity entity);

    ProductEntity saveProduct(ProductTransformedCriteria criteria);

    void deleteProduct(UUID id);

}
