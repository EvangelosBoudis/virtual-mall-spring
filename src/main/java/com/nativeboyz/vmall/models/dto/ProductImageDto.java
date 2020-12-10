package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.ProductImageEntity;

public class ProductImageDto {

    protected String name;
    protected int priorityLevel;

    public ProductImageDto(ProductImageEntity entity) {
        name = entity.getImageName();
        priorityLevel = entity.getPriorityLevel();
    }

    public ProductImageDto(String name, int priorityLevel) {
        this.name = name;
        this.priorityLevel = priorityLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String toString() {
        return "ProductImageDto{" +
                "name='" + name + '\'' +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}
