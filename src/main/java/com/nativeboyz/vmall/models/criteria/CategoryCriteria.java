package com.nativeboyz.vmall.models.criteria;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryCriteria {

    @NotNull
    private MultipartFile file;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    public CategoryCriteria() { }

    public CategoryCriteria(@NotNull MultipartFile file, @NotNull @NotBlank String name, String description) {
        this.file = file;
        this.name = name;
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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
                "file=" + file +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
