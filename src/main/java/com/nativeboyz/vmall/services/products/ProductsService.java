package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.product.ProductTransformedCriteria;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductsService {

    ProductEntity findProduct(UUID id);

    Page<ProductEntity> findProducts(Pageable pageable);

    ProductEntity saveProduct(ProductEntity entity);

    ProductEntity saveProduct(ProductTransformedCriteria criteria);

    void deleteProduct(UUID id);

}
