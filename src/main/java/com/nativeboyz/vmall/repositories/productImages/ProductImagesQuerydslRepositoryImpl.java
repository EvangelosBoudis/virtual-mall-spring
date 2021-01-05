package com.nativeboyz.vmall.repositories.productImages;

import com.nativeboyz.vmall.models.entities.ProductImageEntity;
import com.nativeboyz.vmall.models.entities.QProductImageEntity;
import com.nativeboyz.vmall.tools.QuerydslRepository;

import java.util.List;
import java.util.UUID;

public class ProductImagesQuerydslRepositoryImpl extends QuerydslRepository<ProductImageEntity> implements ProductImagesQuerydslRepository {

    public ProductImagesQuerydslRepositoryImpl() {
        super(ProductImageEntity.class);
    }

    /*
    * JpaRepository Version:
    * @Query("SELECT p FROM ProductImageEntity AS p WHERE p.productEntity.id = :id")
    * List<ProductImageEntity> findAllByProductId(@Param("id") UUID id);
    * */

    @Override
    public List<ProductImageEntity> findAllByProductId(UUID productId) {
        QProductImageEntity productImage = QProductImageEntity.productImageEntity;
        return from(productImage)
                .where(productImage.productEntity.id.eq(productId))
                .fetch();
    }

}
