package com.nativeboyz.vmall.models.criteria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryCriteria {

    @NotNull
    @NotBlank
    private String name;

    private String description;

    public CategoryCriteria() { }

    public CategoryCriteria(@NotNull @NotBlank String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CategoryCriteria{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
