package com.nativeboyz.vmall.specifications;

import com.nativeboyz.vmall.models.entities.ProductDetailsEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.entities.ViewEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewsSpecification implements Specification<ViewEntity> {

    private final UUID customerId;
    private final String searchKey;

    public ViewsSpecification(UUID customerId, String searchKey) {
        this.customerId = customerId;
        this.searchKey = searchKey;
    }

    @Override
    public Predicate toPredicate(
            Root<ViewEntity> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder
    ) {

        List<Predicate> whereClause = new ArrayList<>(){{
            add(builder.equal(root.get("id").get("customerId"), customerId));
        }};

        if (searchKey != null) {
            Subquery<ProductEntity> subQuery = query.subquery(ProductEntity.class);
            Root<ProductEntity> product = subQuery.from(ProductEntity.class);
            Join<ProductEntity, ProductDetailsEntity> details = product.join("detailsEntity");

            String transformedSearchKey = "%" + searchKey.toLowerCase() + "%";
            subQuery.select(product.get("id"))
                    .distinct(true)
                    .where(
                            builder.or(
                                    builder.like(builder.lower(product.get("title")), transformedSearchKey),
                                    builder.like(builder.lower(details.get("keywords")), transformedSearchKey)
                            )
                    );
            whereClause.add(
                    builder.in(root.get("id").get("productId")).value(subQuery)
            );
        }

        return builder.and(whereClause.toArray(Predicate[]::new));
    }

}
