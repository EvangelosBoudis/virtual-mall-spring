package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductDto {

    protected UUID id;
    protected String title;
    protected Float price;
    protected Timestamp uploadTime;

    protected UUID ownerId;
    protected List<UUID> categories;
    protected List<OrderedImage> images;

    protected ProductAdditionalInfo additionalInfo;

    public ProductDto(ProductEntity entity) {
        id = entity.getId();
        title = entity.getTitle();
        price = entity.getPrice();
        uploadTime = entity.getUploadTime();

        ownerId = entity.getCustomerEntity().getId();

        images = entity.getImageEntities()
                .stream()
                .map(OrderedImage::new)
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

    public List<OrderedImage> getImages() {
        return images;
    }

    public void setImages(List<OrderedImage> images) {
        this.images = images;
    }

    public ProductAdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(ProductAdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", uploadTime=" + uploadTime +
                ", ownerId=" + ownerId +
                ", categories=" + categories +
                ", images=" + images +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}
