package com.nativeboyz.vmall.repositories.views;

import com.nativeboyz.vmall.models.dto.CountDto;
import com.nativeboyz.vmall.models.entities.ViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ViewsQuerydslRepository {

    Page<ViewEntity> findAllByCustomerId(UUID customerId, String searchKey, Pageable pageable);

    List<ViewEntity> findAllByProductId(List<UUID> productIds);

    List<CountDto> findAllCountDtoByProductIds(List<UUID> productIds);

    long findCountByProductId(UUID productId);

}
