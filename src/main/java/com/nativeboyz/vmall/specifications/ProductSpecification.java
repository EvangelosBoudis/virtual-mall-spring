package com.nativeboyz.vmall.specifications;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.ProductDetailsEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The ProductSpecification implements Criteria queries based
 * on optional criteria.
 * For JPQL version see also:
 * @see com.nativeboyz.vmall.repositories.products.ProductsRepository
 */

public class ProductSpecification implements Specification<ProductEntity> {

    private final UUID categoryId;
    private final String searchKey;

    public ProductSpecification(UUID categoryId, String searchKey) {
        this.categoryId = categoryId;
        this.searchKey = searchKey;
    }

    @Override
    public Predicate toPredicate(
            Root<ProductEntity> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder
    ) {

        Join<ProductEntity, CategoryEntity> categories = root.join("categoryEntities");
        Join<ProductEntity, ProductDetailsEntity> details = root.join("detailsEntity");

        List<Predicate> predicates = new ArrayList<>();

        if (categoryId != null) {
            predicates.add(
                    builder.equal(categories.get("id"), categoryId)
            );
        }

        if (searchKey != null) {
            String transformedSearchKey = "%" + searchKey.toLowerCase() + "%";
            Predicate titlePredicate = builder.like(
                    builder.lower(root.get("title")),
                    transformedSearchKey
            );
            Predicate keywordsPredicate = builder.like(
                    builder.lower(details.get("keywords")),
                    transformedSearchKey
            );
            predicates.add(
                    builder.or(titlePredicate, keywordsPredicate)
            );
        }

        return builder.and(predicates.toArray(Predicate[]::new));
    }

}
