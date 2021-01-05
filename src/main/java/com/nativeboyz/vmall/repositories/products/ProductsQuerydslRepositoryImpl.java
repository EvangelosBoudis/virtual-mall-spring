package com.nativeboyz.vmall.repositories.products;

import com.nativeboyz.vmall.tools.QuerydslRepository;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.entities.QProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ProductsQuerydslRepositoryImpl extends QuerydslRepository<ProductEntity> implements ProductsQuerydslRepository {

    public ProductsQuerydslRepositoryImpl() {
        super(ProductEntity.class);
    }

    /*
    * JpaRepository Version:
    * @Query(value = "SELECT p FROM ProductEntity AS p WHERE p.customerEntity.id = :customerId")
    * Page<ProductEntity> findAllByCustomerId(@Param("customerId") UUID customerId, Pageable pageable);
    * */

    @Override
    public Page<ProductEntity> findAllByCustomerId(UUID customerId, Pageable pageable) {
        QProductEntity product = QProductEntity.productEntity;
        return executePageQuery(
                from(product).where(product.customerEntity.id.eq(customerId)),
                pageable
        );
    }

}
