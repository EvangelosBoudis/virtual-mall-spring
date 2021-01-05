package com.nativeboyz.vmall.repositories.rates;

import com.nativeboyz.vmall.models.entities.RateEntity;

import java.util.List;
import java.util.UUID;

public interface RatesQuerydslRepository {

    List<RateEntity> findAllByProductId(List<UUID> productIds);

    Double findAvgRateByProductId(UUID productId);

}
