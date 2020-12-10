package com.nativeboyz.vmall.repositories.products;

import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "SELECT p FROM ProductEntity AS p WHERE p.customerEntity.id = :id")
    Page<ProductEntity> findByCustomerId(@Param("id") UUID id, Pageable pageable);
    // countQuery = "SELECT COUNT(p) FROM ProductEntity AS p WHERE p.customerEntity.id = :id"

    @Query(value = "SELECT p FROM ProductEntity AS p JOIN p.categoryEntities AS c WHERE c.id = :id")
    Page<ProductEntity> findByCategoryId(@Param("id") UUID id, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity AS p WHERE LOWER(p.name) LIKE %:text% OR LOWER(productInfoEntity.hashTags) LIKE %:text%")
    Page<ProductEntity> findByTextMatch(@Param("text") String text, Pageable pageable);

    // TODO: Change hashTags type to String inside ProductInfoEntity & Transform on ProductInfoDto

}
