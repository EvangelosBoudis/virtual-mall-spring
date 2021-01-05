package com.nativeboyz.vmall.repositories.views;

import com.nativeboyz.vmall.models.entities.ViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ViewsQuerydslRepository {

    List<ViewEntity> findAllByProductId(List<UUID> productIds);

    long findCountByProductId(UUID productId);

    Page<ViewEntity> findAllByCustomerId(UUID customerId, String searchKey, Pageable pageable);

}
