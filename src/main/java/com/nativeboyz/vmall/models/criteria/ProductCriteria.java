package com.nativeboyz.vmall.models.criteria;

import com.nativeboyz.vmall.models.ImageAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
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
    protected List<UUID> categories;

    @NotNull
    protected List<ImageAction> imageActions;

    @NotNull
    @NotBlank
    protected String description;

    protected String details;

    protected List<String> hashTags;

    public ProductCriteria(
            @NotNull UUID ownerId,
            @NotNull @NotBlank String name,
            @NotNull Float price,
            @NotNull @NotEmpty List<UUID> categories,
            @NotNull List<ImageAction> imageActions,
            @NotNull @NotBlank String description,
            String details,
            List<String> hashTags
    ) {
        this.ownerId = ownerId;
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.imageActions = imageActions;
        this.description = description;
        this.details = details;
        this.hashTags = hashTags;
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

    public List<UUID> getCategories() {
        return categories;
    }

    public void setCategories(List<UUID> categories) {
        this.categories = categories;
    }

    public List<ImageAction> getImageActions() {
        return imageActions;
    }

    public void setImageActions(List<ImageAction> imageActions) {
        this.imageActions = imageActions;
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

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                "ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", imageActions=" + imageActions +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + hashTags +
                '}';
    }
}
