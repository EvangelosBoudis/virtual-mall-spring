package com.nativeboyz.vmall.models.criteria;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class QueryCriteria {

    // paging
    private Integer page = 0;
    private Integer size = 10;

    // filtering
    private String textMatch;
    private UUID categoryId;

    // sorting
    private String sort; // name | price | uploadTime

    public QueryCriteria() { }

    public QueryCriteria(Integer page, Integer size, String textMatch, UUID categoryId, String sort) {
        this.page = page;
        this.size = size;
        this.textMatch = textMatch;
        this.categoryId = categoryId;
        this.sort = sort;
    }

    public Pageable getPageable() {
        if (sort != null) {
            String[] array = sort.split(",");
            if (array.length % 2 == 0) {
                List<Sort.Order> orders = new ArrayList<>();
                for (int i = 0; i < array.length; i = i + 2) {
                    try {
                        Sort.Direction direction = Sort.Direction.fromString(array[i+1]);
                        Sort.Order order = Sort.Order.by(array[i]).with(direction);
                        orders.add(order);
                    } catch (IllegalArgumentException ignore) { }
                }
                return PageRequest.of(page, size, Sort.by(orders));
            }
        }
        return PageRequest.of(page, size);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTextMatch() {
        return textMatch;
    }

    public void setTextMatch(String textMatch) {
        this.textMatch = textMatch;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "QueryCriteria{" +
                "page=" + page +
                ", size=" + size +
                ", textMatch='" + textMatch + '\'' +
                ", categoryId=" + categoryId +
                ", sort='" + sort + '\'' +
                '}';
    }
}

// Sort.Direction.ASC | Sort.Order.asc("name")