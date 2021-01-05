package com.nativeboyz.vmall.repositories.favorites;

import com.nativeboyz.vmall.models.dto.CountDto;
import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FavoritesQuerydslRepository {

    Page<FavoriteEntity> findAllByCustomerId(UUID customerId, String searchKey, Pageable pageable);

    List<FavoriteEntity> findAllByProductId(List<UUID> productIds);

    List<CountDto> findAllCountDtoByProductIds(List<UUID> productIds);

    long findCountByProductId(UUID productId);

    long findCountByProductIdAndCustomerId(UUID productId, UUID customerId);

}
