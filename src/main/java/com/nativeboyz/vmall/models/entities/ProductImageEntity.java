package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nativeboyz.vmall.models.dto.ProductImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "products_images")
public class ProductImageEntity {

    private final static Logger logger = LoggerFactory.getLogger(ProductImageEntity.class);

    @Id
    @Column(name = "image_name", length = 100)
    private String imageName;

    @Column(name = "priority_level")
    private Integer priorityLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public ProductImageEntity() { }

    public ProductImageEntity(String imageName, Integer priorityLevel) {
        this.imageName = imageName;
        this.priorityLevel = priorityLevel;
    }

    public ProductImageEntity(ProductImageDto dto) {
        imageName = dto.getName();
        priorityLevel = dto.getPriorityLevel();
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    @JsonBackReference
    public ProductEntity getProductEntity() {
        logger.info("getProductEntity");
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    @Override
    public String toString() {
        return "ProductImageEntity{" +
                "imageName='" + imageName + '\'' +
                ", priorityLevel=" + priorityLevel +
                ", productEntity=" + productEntity +
                '}';
    }
}
