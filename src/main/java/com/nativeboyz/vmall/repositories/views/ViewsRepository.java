package com.nativeboyz.vmall.repositories.views;

import com.nativeboyz.vmall.models.entities.ViewEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewsRepository extends JpaRepository<ViewEntity, CustomerProductIdentity>, ViewsQuerydslRepository {

}
