package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private ProductEntity productEntity;

    public ProductImageEntity() { }

    public ProductImageEntity(ProductEntity productEntity, String imageName, Integer priorityLevel) {
        this.productEntity = productEntity;
        this.imageName = imageName;
        this.priorityLevel = priorityLevel;
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
