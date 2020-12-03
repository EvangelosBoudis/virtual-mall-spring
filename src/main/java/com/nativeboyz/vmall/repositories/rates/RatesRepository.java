package com.nativeboyz.vmall.repositories.rates;

import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RatesRepository extends JpaRepository<RateEntity, CustomerProductIdentity> {

    @Query("SELECT AVG(r.rate) FROM RateEntity AS r WHERE r.id.productId = :id")
    Float averageRateByProductId(@Param("id") UUID id);

}
