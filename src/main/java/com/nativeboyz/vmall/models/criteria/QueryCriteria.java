package com.nativeboyz.vmall.models.criteria;

import java.util.*;

public class QueryCriteria extends PageCriteria {

    // filtering
    private String searchKey;
    private UUID categoryId;

    public QueryCriteria() { }

    public QueryCriteria(Integer page, Integer size, String sort, String searchKey, UUID categoryId) {
        super(page, size, sort);
        this.searchKey = searchKey;
        this.categoryId = categoryId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "QueryCriteria{" +
                "searchKey='" + searchKey + '\'' +
                ", categoryId=" + categoryId +
                ", page=" + page +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                '}';
    }
}

