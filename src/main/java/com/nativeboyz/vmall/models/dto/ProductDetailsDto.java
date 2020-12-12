package com.nativeboyz.vmall.models.dto;

import com.nativeboyz.vmall.models.entities.ProductEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDetailsDto extends ProductDto {

    private String description;
    private String characteristics;
    private List<String> keywords;

    public ProductDetailsDto(ProductEntity entity) {
        super(entity);
        description = entity.getDetailsEntity().getDescription();
        characteristics = entity.getDetailsEntity().getCharacteristics();
        String textKeywords = entity.getDetailsEntity().getKeywords();
        keywords = (textKeywords != null) ? Arrays.asList(textKeywords.split(",")) : new ArrayList<>();
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
        return "ProductDetailsDto{" +
                "description='" + description + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", keywords=" + keywords +
                ", id=" + id +
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
