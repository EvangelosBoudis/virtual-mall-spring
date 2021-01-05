package com.nativeboyz.vmall.repositories.products;

import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductsQuerydslRepository {

    Page<ProductEntity> findAllByCustomerId(UUID customerId, Pageable pageable);

}
