package com.nativeboyz.vmall.repositories.productImages;

import com.nativeboyz.vmall.models.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductImagesRepository extends JpaRepository<ProductImageEntity, String> {

    @Query("SELECT p FROM ProductImageEntity AS p WHERE p.productEntity.id = :id")
    List<ProductImageEntity> findByProductId(@Param("id") UUID id);

}
