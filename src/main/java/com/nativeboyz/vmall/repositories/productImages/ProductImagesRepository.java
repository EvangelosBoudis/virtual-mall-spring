package com.nativeboyz.vmall.repositories.productImages;

import com.nativeboyz.vmall.models.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImageEntity, String>, ProductImagesQuerydslRepository {

}
