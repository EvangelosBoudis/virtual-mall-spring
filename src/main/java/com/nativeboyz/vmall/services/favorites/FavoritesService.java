package com.nativeboyz.vmall.services.favorites;

import com.nativeboyz.vmall.models.criteria.FavoriteCriteria;
import com.nativeboyz.vmall.models.entities.FavoriteEntity;

import java.util.Optional;

public interface FavoritesService {

    Optional<FavoriteEntity> saveFavorite(FavoriteCriteria criteria);

}
