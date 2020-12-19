package com.nativeboyz.vmall.repositories.test;

import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductsCriteriaRepository {

    Page<ProductEntity> findByCategoryIdAndTextMatch(UUID categoryId, String textMatch, Pageable pageable);

}
