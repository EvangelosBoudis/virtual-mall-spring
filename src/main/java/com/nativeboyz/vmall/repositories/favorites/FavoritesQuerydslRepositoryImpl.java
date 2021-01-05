package com.nativeboyz.vmall.repositories.favorites;

import com.nativeboyz.vmall.models.entities.*;
import com.nativeboyz.vmall.models.entities.QFavoriteEntity;
import com.nativeboyz.vmall.querydslExpressions.ProductExpressions;
import com.nativeboyz.vmall.tools.QuerydslRepository;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public class FavoritesQuerydslRepositoryImpl extends QuerydslRepository<FavoriteEntity> implements FavoritesQuerydslRepository {

    public FavoritesQuerydslRepositoryImpl() {
        super(FavoriteEntity.class);
    }

    /*
    * JpaRepository Version:
    * @Query(value =
    *    "SELECT f FROM FavoriteEntity AS f WHERE f.id.customerId = :id AND f.status = TRUE AND f.id.productId IN " +
    *    "(SELECT p.id FROM ProductEntity AS p JOIN p.detailsEntity AS d WHERE LOWER(d.keywords) LIKE %:searchKey% OR LOWER(p.title) LIKE %:searchKey%)")
    * Page<FavoriteEntity> findAllByCustomerId(@Param("id") UUID id, @Param("searchKey") String searchKey, Pageable pageable);
    * */

    @Override
    public Page<FavoriteEntity> findAllByCustomerId(UUID customerId, String searchKey, Pageable pageable) {

        QFavoriteEntity favorite = QFavoriteEntity.favoriteEntity;
        JPQLQuery<UUID> subQuery = ProductExpressions.productIdsWhichSatisfiesSearch(searchKey);

        JPQLQuery<FavoriteEntity> query =
                from(favorite)
                .where(favorite.id.customerId.eq(customerId)
                        .and(favorite.status.eq(true))
                        .and(favorite.id.productId.in(subQuery))
                );

        return executePageQuery(query, pageable);
    }

    /*
     * JpaRepository Version:
     * @Query("SELECT f FROM FavoriteEntity AS f WHERE f.id.productId IN :ids")
     * List<FavoriteEntity> findAllByProductId(@Param("ids") List<UUID> ids);
     * */

    @Override
    public List<FavoriteEntity> findAllByProductId(List<UUID> productIds) {
        QFavoriteEntity favorite = QFavoriteEntity.favoriteEntity;
        return from(favorite)
                .where(favorite.id.productId.in(productIds))
                .fetch();
    }

    /*
    * JpaRepository Version:
    * @Query("SELECT COUNT(f) FROM FavoriteEntity AS f WHERE f.id.productId = :id AND f.status = TRUE")
    * Integer countByProductId(@Param("id") UUID id);
    * */

    @Override
    public long findCountByProductId(UUID productId) {
        QFavoriteEntity favorite = QFavoriteEntity.favoriteEntity;
        return from(favorite)
                .where(favorite.id.productId.eq(productId).and(favorite.status.eq(true)))
                .fetchCount();
    }

    /*
    * JpaRepository Version:
    * @Query("SELECT COUNT(f) FROM FavoriteEntity AS f WHERE " +
    *    "f.id.customerId = :customerId AND " +
    *    "f.id.productId = :productId AND " +
    *    "f.status = TRUE")
    * Integer countByProductIdAndCustomerId(@Param("productId") UUID productId, @Param("customerId") UUID customerId);
    * */

    @Override
    public long findCountByProductIdAndCustomerId(UUID productId, UUID customerId) {
        QFavoriteEntity favorite = QFavoriteEntity.favoriteEntity;
        return from(favorite)
                .where(favorite.id.productId.eq(productId)
                        .and(favorite.id.customerId.eq(customerId))
                        .and(favorite.status.eq(true))
                ).fetchCount();
    }

}

    /* Cross Join Hibernate Bug
    * "SELECT r FROM FavoriteEntity AS r WHERE r.id.customerId = :id AND f.status = TRUE " +
      "AND r.id.productId IN " +
      "(SELECT p.id FROM ProductEntity AS p WHERE LOWER(p.title) LIKE %:searchKey% OR LOWER(p.detailsEntity.keywords) LIKE %:searchKey%)"
    * */
