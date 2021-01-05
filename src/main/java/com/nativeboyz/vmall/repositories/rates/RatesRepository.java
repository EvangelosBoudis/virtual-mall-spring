package com.nativeboyz.vmall.repositories.rates;

import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatesRepository extends JpaRepository<RateEntity, CustomerProductIdentity>, RatesQuerydslRepository {

}
