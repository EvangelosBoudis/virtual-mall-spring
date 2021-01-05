package com.nativeboyz.vmall.repositories.products;

import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID>, ProductsQuerydslRepository {

    @Query(countName = "ProductEntity.countBySearchKey", nativeQuery = true)
    Page<ProductEntity> findAllBySearchKey(@Param("searchKey") String searchKey, Pageable pageable);

    @Query(countName = "ProductEntity.countByCategoryId", nativeQuery = true)
    Page<ProductEntity> findAllByCategoryId(@Param("categoryId") UUID categoryId, Pageable pageable);

    @Query(countName = "ProductEntity.countByCategoryIdAndSearchKey", nativeQuery = true)
    Page<ProductEntity> findAllByCategoryIdAndSearchKey(@Param("categoryId") UUID categoryId, @Param("searchKey") String searchKey, Pageable pageable);

}
