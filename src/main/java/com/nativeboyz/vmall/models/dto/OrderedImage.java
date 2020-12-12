package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.ProductImageEntity;

import javax.validation.constraints.NotNull;

public class OrderedImage {

    @NotNull
    protected String name;

    protected int order;

    public OrderedImage(ProductImageEntity entity) {
        name = entity.getImageName();
        order = entity.getPriorityLevel();
    }

    public OrderedImage(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderedImage{" +
                "name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
