package com.nativeboyz.vmall.repositories.rates;

import com.nativeboyz.vmall.models.dto.RateDto;
import com.nativeboyz.vmall.models.entities.QRateEntity;
import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.tools.QuerydslRepository;
import com.querydsl.core.types.Projections;

import java.util.List;
import java.util.UUID;

public class RatesQuerydslRepositoryImpl extends QuerydslRepository<RateEntity> implements RatesQuerydslRepository {

    public RatesQuerydslRepositoryImpl() {
        super(RateEntity.class);
    }

    /*
    * JpaRepository Version:
    * @Query("SELECT r FROM RateEntity AS r WHERE r.id.productId IN :ids")
    * List<RateEntity> findAllByProductId(@Param("ids") List<UUID> ids);
    * */

    @Override
    public List<RateEntity> findAllByProductId(List<UUID> productIds) {
        QRateEntity rate = QRateEntity.rateEntity;
        return from(rate)
                .where(rate.id.productId.in(productIds))
                .fetch();
    }

    /*
     * JpaRepository Version:
     * @Query("SELECT r.product_id, AVG(r.rate) FROM RateEntity AS r WHERE r.id.productId IN :productIds GROUP BY r.product_id")
     * List<RateDto> findAllDtoByProductIds(List<UUID> productIds)
     * */

    @Override
    public List<RateDto> findAllRateDtoByProductIds(List<UUID> productIds) {
        QRateEntity rate = QRateEntity.rateEntity;
        return from(rate)
                .select(Projections.fields(RateDto.class, rate.id.productId, rate.rate.avg().as("avg")))
                .where(rate.id.productId.in(productIds))
                .groupBy(rate.id.productId)
                .fetch();
    }

    /*
     * JpaRepository Version:
     * @Query("SELECT AVG(r.rate) FROM RateEntity AS r WHERE r.id.productId = :id")
     * Float findAvgRateByProductId(@Param("id") UUID id);
     * */

    @Override
    public Double findAvgRateByProductId(UUID productId) {
        QRateEntity rate = QRateEntity.rateEntity;
        return from(rate)
                .select(rate.rate.avg())
                .where(rate.id.productId.eq(productId))
                .fetchOne();
    }

}
