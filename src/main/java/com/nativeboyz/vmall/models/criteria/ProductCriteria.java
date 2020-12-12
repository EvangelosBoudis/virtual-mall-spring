package com.nativeboyz.vmall.models.criteria;

import com.nativeboyz.vmall.models.dto.OrderedImage;

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
    protected String title;

    @NotNull
    protected Float price;

    @NotNull
    @NotEmpty
    protected List<UUID> categories;

    @NotNull
    protected List<OrderedImage> orderedImages;

    @NotNull
    @NotBlank
    protected String description;

    protected String characteristics;

    @NotNull
    protected List<String> keywords;

    public ProductCriteria(
            @NotNull UUID ownerId,
            @NotNull @NotBlank String title,
            @NotNull Float price,
            @NotNull @NotEmpty List<UUID> categories,
            @NotNull List<OrderedImage> orderedImages,
            @NotNull @NotBlank String description,
            String characteristics,
            @NotNull List<String> keywords
    ) {
        this.ownerId = ownerId;
        this.title = title;
        this.price = price;
        this.categories = categories;
        this.orderedImages = orderedImages;
        this.description = description;
        this.characteristics = characteristics;
        this.keywords = keywords;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<OrderedImage> getOrderedImages() {
        return orderedImages;
    }

    public void setOrderedImages(List<OrderedImage> orderedImages) {
        this.orderedImages = orderedImages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                "ownerId=" + ownerId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", orderedImages=" + orderedImages +
                ", description='" + description + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
