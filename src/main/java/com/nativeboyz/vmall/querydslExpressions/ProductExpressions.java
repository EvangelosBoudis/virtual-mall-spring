package com.nativeboyz.vmall.querydslExpressions;

import com.nativeboyz.vmall.models.entities.QProductEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import java.util.UUID;

public class ProductExpressions {

    public static JPQLQuery<UUID> productIdsWhichSatisfiesSearch(String searchKey) {

        QProductEntity product = QProductEntity.productEntity;

        String lowerSearchKey = searchKey != null ? searchKey.toLowerCase() : "";

        return JPAExpressions
                .select(product.id)
                .from(product)
                .join(product.detailsEntity) // hibernate bug (if join not applied manually, cross join takes place)
                .where(product.title.lower().contains(lowerSearchKey).or(product.detailsEntity.keywords.lower().contains(lowerSearchKey)));

    }

}
