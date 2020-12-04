package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.entities.ProductImageEntity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductInfoDto extends ProductDto {

    private String description;
    private String details;
    private String[] hashTags;

    public ProductInfoDto(ProductEntity productEntity) {

        super(productEntity.getId(), productEntity.getName(),
                productEntity.getPrice(), productEntity.getUploadTime());

        ownerId = productEntity.getCustomerEntity().getId();

        images = productEntity.getProductImageEntities()
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList()).toArray(String[]::new);

        categories = productEntity.getCategoryEntities()
                .stream()
                .map(CategoryEntity::getId)
                .collect(Collectors.toList()).toArray(UUID[]::new);

        description = productEntity.getProductInfoEntity().getDescription();
        details = productEntity.getProductInfoEntity().getDetails();
        hashTags = productEntity.getProductInfoEntity().getHashTags();
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
        return "ProductInfoDto{" +
                "description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", ownerId=" + ownerId +
                ", images=" + Arrays.toString(images) +
                ", categories=" + Arrays.toString(categories) +
                ", viewsQty=" + viewsQty +
                ", favoritesQty=" + favoritesQty +
                ", avgRate=" + avgRate +
                ", favorite=" + favorite +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
