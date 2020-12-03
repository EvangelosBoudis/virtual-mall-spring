package com.nativeboyz.vmall.models.criteria.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

public class ProductCriteria {

    @NotNull
    @NotBlank
    protected String name;

    @NotNull
    protected Float price;

    @NotNull
    @NotBlank
    protected String description;

    protected String details;

    protected String[] hashTags;

    @NotNull
    @NotEmpty
    protected UUID[] categories;

    @NotNull
    protected UUID uploaderId; // TODO: Retrieve from JWT

    public ProductCriteria(@NotNull @NotBlank String name, @NotNull Float price, @NotNull @NotBlank String description, String details, String[] hashTags, @NotNull @NotBlank UUID[] categories, @NotNull @NotBlank UUID uploaderId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.details = details;
        this.hashTags = hashTags;
        this.categories = categories;
        this.uploaderId = uploaderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String[] getHashTags() {
        return hashTags;
    }

    public void setHashTags(String[] hashTags) {
        this.hashTags = hashTags;
    }

    public UUID[] getCategories() {
        return categories;
    }

    public void setCategories(UUID[] categories) {
        this.categories = categories;
    }

    public UUID getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(UUID uploaderId) {
        this.uploaderId = uploaderId;
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", categories=" + Arrays.toString(categories) +
                ", uploaderId=" + uploaderId +
                '}';
    }
}
