package com.nativeboyz.vmall.specifications;

import com.nativeboyz.vmall.models.entities.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The FavoriteSpecification implements Criteria queries based
 * on optional criteria.
 * For JPQL version see also:
 * @see com.nativeboyz.vmall.repositories.favorites.FavoritesRepository
 */

public class FavoriteSpecification implements Specification<FavoriteEntity> {

    private final UUID customerId;
    private final String searchKey;

    public FavoriteSpecification(UUID customerId, String searchKey) {
        this.customerId = customerId;
        this.searchKey = searchKey;
    }

    @Override
    public Predicate toPredicate(
            Root<FavoriteEntity> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder
    ) {

        List<Predicate> whereClause = new ArrayList<>(){{
            add(builder.equal(root.get("id").get("customerId"), customerId));
            add(builder.equal(root.get("status"), true));
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
