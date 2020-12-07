package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.ProductEntity;

import java.util.Arrays;

public class ProductInfoDto extends ProductDto {

    private String description;
    private String details;
    private String[] hashTags;

    public ProductInfoDto(ProductEntity entity) {
        super(entity);
        description = entity.getProductInfoEntity().getDescription();
        details = entity.getProductInfoEntity().getDetails();
        hashTags = entity.getProductInfoEntity().getHashTags();
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

    @Override
    public String toString() {
        return "ProductInfoDto{" +
                "description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", uploadTime=" + uploadTime +
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
