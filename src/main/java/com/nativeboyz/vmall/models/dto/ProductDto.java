package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.entities.ProductImageEntity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductDto {

    private UUID id;
    private String name;
    private Float price;
    private Timestamp uploadTime;
    private String description;
    private String details;
    private String[] hashTags;

    private UUID ownerId;
    private String[] images;
    private UUID[] categories;

    private Integer viewsQty;
    private Integer favoritesQty;
    private Float avgRate;
    private Boolean favorite;

    public ProductDto(ProductEntity entity, Integer viewsQty, Integer favoritesQty, Float avgRate, Boolean favorite) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        uploadTime = entity.getUploadTime();
        description = entity.getDescription();
        details = entity.getDetails();
        hashTags = entity.getHashTags();
        ownerId = entity.getCustomerEntity().getId();

        images = entity.getProductImageEntities()
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList()).toArray(String[]::new);

        categories = entity.getCategoryEntities()
                .stream()
                .map(CategoryEntity::getId)
                .collect(Collectors.toList()).toArray(UUID[]::new);

        this.viewsQty = viewsQty;
        this.favoritesQty = favoritesQty;
        this.avgRate = avgRate;
        this.favorite = favorite;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
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

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public UUID[] getCategories() {
        return categories;
    }

    public void setCategories(UUID[] categories) {
        this.categories = categories;
    }

    public Integer getViewsQty() {
        return viewsQty;
    }

    public void setViewsQty(Integer viewsQty) {
        this.viewsQty = viewsQty;
    }

    public Integer getFavoritesQty() {
        return favoritesQty;
    }

    public void setFavoritesQty(Integer favoritesQty) {
        this.favoritesQty = favoritesQty;
    }

    public Float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Float avgRate) {
        this.avgRate = avgRate;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", uploadTime=" + uploadTime +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", ownerId=" + ownerId +
                ", images=" + Arrays.toString(images) +
                ", categories=" + Arrays.toString(categories) +
                ", viewsQty=" + viewsQty +
                ", favoritesQty=" + favoritesQty +
                ", avgRate=" + avgRate +
                ", favorite=" + favorite +
                '}';
    }
}
