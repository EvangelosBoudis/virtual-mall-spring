package com.nativeboyz.vmall.repositories.favorites;

import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<FavoriteEntity, CustomerProductIdentity>, FavoritesQuerydslRepository {

}