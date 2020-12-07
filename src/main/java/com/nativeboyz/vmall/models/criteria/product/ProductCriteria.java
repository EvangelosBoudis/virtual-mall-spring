package com.nativeboyz.vmall.models.criteria.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

public class ProductCriteria {

    @NotNull
    protected UUID ownerId; // TODO: Retrieve from JWT

    @NotNull
    @NotBlank
    protected String name;

    @NotNull
    protected Float price;

    @NotNull
    @NotEmpty
    protected UUID[] categories;

    @NotNull
    @NotBlank
    protected String description;

    protected String details;

    protected String[] hashTags;

    protected String[] previousFileNames;

    public ProductCriteria(
            @NotNull UUID ownerId,
            @NotNull @NotBlank String name,
            @NotNull Float price,
            @NotNull @NotEmpty UUID[] categories,
            @NotNull @NotBlank String description,
            String details,
            String[] hashTags,
            String[] previousFileNames
    ) {
        this.ownerId = ownerId;
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.description = description;
        this.details = details;
        this.hashTags = hashTags;
        this.previousFileNames = previousFileNames;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
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

    public UUID[] getCategories() {
        return categories;
    }

    public void setCategories(UUID[] categories) {
        this.categories = categories;
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

    public String[] getPreviousFileNames() {
        return previousFileNames;
    }

    public void setPreviousFileNames(String[] previousFileNames) {
        this.previousFileNames = previousFileNames;
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                "ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + Arrays.toString(categories) +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", previousFileNames=" + Arrays.toString(previousFileNames) +
                '}';
    }
}
