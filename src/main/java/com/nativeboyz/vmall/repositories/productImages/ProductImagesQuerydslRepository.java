package com.nativeboyz.vmall.repositories.productImages;

import com.nativeboyz.vmall.models.entities.ProductImageEntity;

import java.util.List;
import java.util.UUID;

public interface ProductImagesQuerydslRepository {

    List<ProductImageEntity> findAllByProductId(UUID productId);

}
