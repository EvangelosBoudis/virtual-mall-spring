package com.nativeboyz.vmall.repositories.views;

import com.nativeboyz.vmall.models.dto.CountDto;
import com.nativeboyz.vmall.models.entities.*;
import com.nativeboyz.vmall.models.entities.QViewEntity;
import com.nativeboyz.vmall.querydslExpressions.ProductExpressions;
import com.nativeboyz.vmall.tools.QuerydslRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public class ViewsQuerydslRepositoryImpl extends QuerydslRepository<ViewEntity> implements ViewsQuerydslRepository {

    public ViewsQuerydslRepositoryImpl() {
        super(ViewEntity.class);
    }

    /*
     * JpaRepository Version:
     * @Query(value =
     *    "SELECT f FROM ViewEntity AS v WHERE v.id.customerId = :id AND v.id.productId IN " +
     *    "(SELECT p.id FROM ProductEntity AS p JOIN p.detailsEntity AS d WHERE LOWER(d.keywords) LIKE %:searchKey% OR LOWER(p.title) LIKE %:searchKey%)")
     * Page<ViewEntity> findAllByCustomerId(@Param("id") UUID id, @Param("searchKey") String searchKey, Pageable pageable);
     * */

    @Override
    public Page<ViewEntity> findAllByCustomerId(UUID customerId, String searchKey, Pageable pageable) {

        QViewEntity view = QViewEntity.viewEntity;

        JPQLQuery<UUID> subQuery = ProductExpressions.productIdsWhichSatisfiesSearch(searchKey);

        JPQLQuery<ViewEntity> query = from(view)
                .where(view.id.customerId.eq(customerId)
                        .and(view.id.productId.in(subQuery))
                );

        return executePageQuery(query, pageable);
    }

    /*
    * JpaRepository Version:
    * @Query("SELECT v FROM ViewEntity AS v WHERE v.id.productId IN :ids")
    * List<ViewEntity> findAllByProductId(@Param("ids") List<UUID> ids);
    * */

    @Override
    public List<ViewEntity> findAllByProductId(List<UUID> productIds) {
        QViewEntity view = QViewEntity.viewEntity;
        return from(view)
                .where(view.id.productId.in(productIds))
                .fetch();
    }

    /*
     * JpaRepository Version:
     * @Query("SELECT v.product_id, COUNT(v.product_id) FROM ViewEntity AS v WHERE v.id.productId IN :productIds AS v GROUP BY v.product_id")
     * List<CountDto> findAllCountDtoByProductIds(@Param("productIds") List<UUID> productIds);
     * */

    @Override
    public List<CountDto> findAllCountDtoByProductIds(List<UUID> productIds) {
        QViewEntity view = QViewEntity.viewEntity;
        return from(view)
                .select(Projections.fields(CountDto.class, view.id.productId, view.id.productId.count().as("count")))
                .where(view.id.productId.in(productIds))
                .groupBy(view.id.productId)
                .fetch();
    }

    /*
    * JpaRepository Version:
    * @Query("SELECT COUNT(v) FROM ViewEntity AS v WHERE v.id.productId = :id")
    * Integer findCountByProductId(@Param("id") UUID id);
    * */

    @Override
    public long findCountByProductId(UUID productId) {
        QViewEntity view = QViewEntity.viewEntity;
        return from(view)
                .where(view.id.productId.eq(productId))
                .fetchCount();
    }

}
