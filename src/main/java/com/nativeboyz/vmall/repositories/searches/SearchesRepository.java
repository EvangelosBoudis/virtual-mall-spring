package com.nativeboyz.vmall.repositories.searches;

import com.nativeboyz.vmall.models.entities.SearchEntity;
import com.nativeboyz.vmall.models.entities.identities.SearchIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchesRepository extends JpaRepository<SearchEntity, SearchIdentity> {

}
