package com.nativeboyz.vmall.models.criteria;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageCriteria {

    // paging
    protected Integer page = 0;
    protected Integer size = 10;

    // sorting
    protected String sort; // title | price | uploadTime

    public PageCriteria() { }

    public PageCriteria(Integer page, Integer size, String sort) {
        this.page = page;
        this.size = size;
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

    // Sort.Direction.ASC | Sort.Order.asc("name")

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "PageCriteria{" +
                "page=" + page +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                '}';
    }
}
