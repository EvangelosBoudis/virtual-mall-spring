package com.nativeboyz.vmall.repositories.products;

import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    @Query(value = "SELECT p FROM ProductEntity AS p JOIN p.categoryEntities AS c WHERE c.id = :categoryId")
    Page<ProductEntity> findAllByCategoryId(@Param("categoryId") UUID categoryId, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity AS p WHERE LOWER(p.title) LIKE %:searchKey% OR LOWER(p.detailsEntity.keywords) LIKE %:searchKey%")
    Page<ProductEntity> findAllBySearchKey(@Param("searchKey") String searchKey, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity AS p WHERE p.customerEntity.id = :customerId")
    Page<ProductEntity> findAllByCustomerId(@Param("customerId") UUID customerId, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity AS p JOIN p.favoriteEntities AS f WHERE f.id.customerId = :customerId")
    Page<ProductEntity> findAllCustomerFavorites(@Param("customerId") UUID customerId, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity AS p JOIN p.viewEntities AS v WHERE v.id.customerId = :customerId")
    Page<ProductEntity> findAllCustomerViews(@Param("customerId") UUID customerId, Pageable pageable);

}

// countQuery = "SELECT COUNT(p) FROM ProductEntity AS p WHERE p.customerEntity.id = :id"
