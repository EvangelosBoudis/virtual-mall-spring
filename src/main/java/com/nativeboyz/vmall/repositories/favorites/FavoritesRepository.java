package com.nativeboyz.vmall.repositories.favorites;

import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FavoritesRepository extends JpaRepository<FavoriteEntity, CustomerProductIdentity>, JpaSpecificationExecutor<FavoriteEntity> {
    
    @Query("SELECT COUNT(f) FROM FavoriteEntity AS f WHERE f.id.productId = :id")
    Integer countByProductId(@Param("id") UUID id);

    @Query("SELECT COUNT(f) FROM FavoriteEntity AS f WHERE " +
            "f.id.customerId = :customerId AND " +
            "f.id.productId = :productId AND " +
            "f.status = TRUE")
    Integer isFavoriteByCustomer(@Param("productId") UUID productId, @Param("customerId") UUID customerId);

    @Query("SELECT f FROM FavoriteEntity AS f WHERE f.id.productId IN :ids")
    List<FavoriteEntity> findAllByProductId(@Param("ids") List<UUID> ids);

    @Query(value =
            "SELECT f FROM FavoriteEntity AS f WHERE f.id.customerId = :id AND f.id.productId IN" +
            "(SELECT p.id FROM ProductEntity AS p JOIN p.detailsEntity AS d WHERE LOWER(d.keywords) LIKE %:searchKey% OR LOWER(p.title) LIKE %:searchKey%)")
    Page<FavoriteEntity> findAllByCustomerId(@Param("id") UUID id, @Param("searchKey") String searchKey, Pageable pageable);

    /* Cross Join Hibernate Bug
    * "SELECT r FROM FavoriteEntity AS r WHERE r.id.customerId = :id " +
      "AND r.id.productId IN " +
      "(SELECT p.id FROM ProductEntity AS p WHERE LOWER(p.title) LIKE %:searchKey% OR LOWER(p.detailsEntity.keywords) LIKE %:searchKey%)"
    * */

}