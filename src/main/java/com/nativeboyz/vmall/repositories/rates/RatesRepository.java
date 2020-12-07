package com.nativeboyz.vmall.repositories.rates;

import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RatesRepository extends JpaRepository<RateEntity, CustomerProductIdentity> {

    @Query("SELECT r FROM RateEntity AS r WHERE r.id.productId IN :ids")
    List<RateEntity> findAllByProductId(@Param("ids") List<UUID> ids);

    @Query("SELECT AVG(r.rate) FROM RateEntity AS r WHERE r.id.productId = :id")
    Float avgRateByProductId(@Param("id") UUID id);

}
