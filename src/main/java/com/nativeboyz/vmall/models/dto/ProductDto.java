package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductDto {

    protected UUID id;
    protected String name;
    protected Float price;
    protected Timestamp uploadTime;

    protected UUID ownerId;
    protected List<UUID> categories;
    protected List<ProductImageDto> images;

    protected Integer viewsQty;
    protected Integer favoritesQty;
    protected Float avgRate;
    protected Boolean favorite;

    public ProductDto(ProductEntity entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        uploadTime = entity.getUploadTime();

        ownerId = entity.getCustomerEntity().getId();

        images = entity.getProductImageEntities()
                .stream()
                .map(ProductImageDto::new)
                .collect(Collectors.toList());

        categories = entity.getCategoryEntities()
                .stream()
                .map(CategoryEntity::getId)
                .collect(Collectors.toList());
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

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public List<UUID> getCategories() {
        return categories;
    }

    public void setCategories(List<UUID> categories) {
        this.categories = categories;
    }

    public List<ProductImageDto> getImages() {
        return images;
    }

    public void setImages(List<ProductImageDto> images) {
        this.images = images;
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
                ", ownerId=" + ownerId +
                ", categories=" + categories +
                ", images=" + images +
                ", viewsQty=" + viewsQty +
                ", favoritesQty=" + favoritesQty +
                ", avgRate=" + avgRate +
                ", favorite=" + favorite +
                '}';
    }
}
