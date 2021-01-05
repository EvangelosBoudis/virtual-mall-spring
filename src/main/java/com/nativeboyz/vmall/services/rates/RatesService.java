package com.nativeboyz.vmall.services.rates;

import com.nativeboyz.vmall.models.criteria.RateCriteria;
import com.nativeboyz.vmall.models.entities.RateEntity;

import java.util.Optional;

public interface RatesService {

    Optional<RateEntity> saveRate(RateCriteria criteria);

}
