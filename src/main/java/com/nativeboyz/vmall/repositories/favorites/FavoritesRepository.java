package com.nativeboyz.vmall.repositories.favorites;

import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FavoritesRepository extends JpaRepository<FavoriteEntity, CustomerProductIdentity> {

    @Query("SELECT f FROM FavoriteEntity AS f WHERE f.id.productId IN :ids")
    List<FavoriteEntity> findAllByProductId(@Param("ids") List<UUID> ids);

    @Query("SELECT COUNT(f) FROM FavoriteEntity AS f WHERE f.id.productId = :id")
    Integer countByProductId(@Param("id") UUID id);

    @Query("SELECT COUNT(f) FROM FavoriteEntity AS f WHERE " +
            "f.id.customerId = :customerId AND " +
            "f.id.productId = :productId AND " +
            "f.status = TRUE")
    Integer isFavoriteByCustomer(@Param("productId") UUID productId, @Param("customerId") UUID customerId);

}
