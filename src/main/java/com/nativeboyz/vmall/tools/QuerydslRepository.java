package com.nativeboyz.vmall.tools;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.*;

// TODO: Check JPASQLQuery, QuerydslJpaPredicateExecutor

public class QuerydslRepository<E> extends QuerydslRepositorySupport {

    public QuerydslRepository(Class<E> domainClass) {
        super(domainClass);
    }

    protected <T> Page<T> executePageQuery(JPQLQuery<T> query, Pageable pageable) {
        if (getQuerydsl() != null) query = getQuerydsl().applyPagination(pageable, query);
        QueryResults<T> queryResult = query.fetchResults();
        return new PageImpl<>(queryResult.getResults(), pageable, queryResult.getTotal());
    }

}
