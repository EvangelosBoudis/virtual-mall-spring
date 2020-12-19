package com.nativeboyz.vmall.repositories.test;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.ProductDetailsEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductsCriteriaRepositoryImpl implements ProductsCriteriaRepository {

    private final EntityManager entityManager;

    @Autowired
    public ProductsCriteriaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<ProductEntity> findByCategoryIdAndTextMatch(UUID categoryId, String textMatch, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        // main query

        CriteriaQuery<ProductEntity> mainQuery = builder.createQuery(ProductEntity.class);
        Root<ProductEntity> mainRoot = mainQuery.from(ProductEntity.class);

        Predicate[] mainPredicate = createPredicateByCategoryAndText(
                builder, mainRoot,
                categoryId, textMatch
        );

        List<Order> order = sortOrderToCriteriaOrder(
                builder, mainRoot,
                pageable.getSort()
        );

        mainQuery.select(mainRoot).where(mainPredicate).orderBy(order);

        TypedQuery<ProductEntity> typedQuery = entityManager.createQuery(mainQuery);
        typedQuery.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());

        // count query

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);

        Predicate[] countPredicate = createPredicateByCategoryAndText(
                builder, countRoot,
                categoryId, textMatch
        );

        countQuery.select(builder.count(countRoot)).where(countPredicate);

        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), pageable, Math.toIntExact(count));
    }

    private <T> Predicate[] createPredicateByCategoryAndText(
            CriteriaBuilder builder, Root<T> root,
            UUID categoryId, String textMatch
    ) {
        Join<ProductEntity, CategoryEntity> categories = root.join("categoryEntities");
        Join<ProductEntity, ProductDetailsEntity> details = root.join("detailsEntity");

        List<Predicate> predicate = new ArrayList<>();

        if (categoryId != null) {
            Predicate categoryPredicate = builder.equal(
                    categories.get("id"),
                    categoryId
            );
            predicate.add(categoryPredicate);
        }

        if (textMatch != null) {
            String transformedTextMatch = "%" + textMatch.toLowerCase() + "%";
            Predicate titlePredicate = builder.like(
                    builder.lower(root.get("title")),
                    transformedTextMatch
            );
            Predicate keywordsPredicate = builder.like(
                    builder.lower(details.get("keywords")),
                    transformedTextMatch
            );
            predicate.add(builder.or(titlePredicate, keywordsPredicate));
        }

        return predicate.toArray(Predicate[]::new);
    }

    private List<Order> sortOrderToCriteriaOrder(CriteriaBuilder cb, Root<?> root, Sort sort) {
        return sort
                .stream()
                .map(sortOrder -> {
                    Path<String> property = root.get(sortOrder.getProperty());
                    return sortOrder.isAscending() ? cb.asc(property) : cb.desc(property);
                }).collect(Collectors.toList());
    }

}