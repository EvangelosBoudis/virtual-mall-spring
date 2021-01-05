package com.nativeboyz.vmall.repositories.rates;

import com.nativeboyz.vmall.models.entities.QRateEntity;
import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.tools.QuerydslRepository;

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
