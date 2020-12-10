package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.ProductEntity;

import java.util.Arrays;
import java.util.List;

public class ProductInfoDto extends ProductDto {

    private String description;
    private String details;
    private List<String> hashTags;

    public ProductInfoDto(ProductEntity entity) {
        super(entity);
        description = entity.getProductInfoEntity().getDescription();
        details = entity.getProductInfoEntity().getDetails();
        hashTags = Arrays.asList(entity.getProductInfoEntity().getHashTags());
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
        return "ProductInfoDto{" +
                "description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + hashTags +
                ", id=" + id +
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
