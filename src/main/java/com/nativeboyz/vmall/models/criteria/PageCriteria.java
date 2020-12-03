package com.nativeboyz.vmall.models.criteria;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageCriteria {

    private Integer page = 0;
    private Integer size = 10;

    public PageCriteria() { }

    public PageCriteria(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Pageable asPageable() {
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

    @Override
    public String toString() {
        return "PageCriteria{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
