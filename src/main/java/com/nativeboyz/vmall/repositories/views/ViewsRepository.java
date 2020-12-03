package com.nativeboyz.vmall.repositories.views;

import com.nativeboyz.vmall.models.entities.ViewEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ViewsRepository extends JpaRepository<ViewEntity, CustomerProductIdentity> {

    @Query("SELECT COUNT(v) FROM ViewEntity AS v WHERE v.id.productId = :id")
    Integer countByProductId(@Param("id") UUID id);

}
