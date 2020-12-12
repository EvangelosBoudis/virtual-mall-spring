package com.nativeboyz.vmall.models.criteria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryCriteria {

    @NotNull
    @NotBlank
    private String title;

    private String description;

    public CategoryCriteria() { }

    public CategoryCriteria(@NotNull @NotBlank String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
