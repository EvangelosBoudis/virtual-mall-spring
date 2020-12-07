package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {

    private final static Logger logger = LoggerFactory.getLogger(ProductEntity.class);

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "upload_time")
    private Timestamp uploadTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
    private ProductInfoEntity productInfoEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private CustomerEntity customerEntity;

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ProductImageEntity> productImageEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categoryEntities;

    @OneToMany(mappedBy = "id.productId", fetch = FetchType.LAZY)
    private Set<FavoriteEntity> favoriteEntities;

    @OneToMany(mappedBy = "id.productId", fetch = FetchType.LAZY)
    private Set<ViewEntity> viewEntities;

    @OneToMany(mappedBy = "id.productId", fetch = FetchType.LAZY)
    private Set<RateEntity> rateEntities;

    public ProductEntity() { }

    public ProductEntity(String name, Float price) {
        this.name = name;
        this.price = price;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
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

    public ProductInfoEntity getProductInfoEntity() {
        return productInfoEntity;
    }

    public void setProductInfoEntity(ProductInfoEntity productInfoEntity) {
        this.productInfoEntity = productInfoEntity;
    }

    @JsonIgnore
    public CustomerEntity getCustomerEntity() {
        logger.info("getCustomerEntity");
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    @JsonManagedReference
    public Set<ProductImageEntity> getProductImageEntities() {
        logger.info("getProductImageEntities");
        return productImageEntities;
    }

    public void setProductImageEntities(Set<ProductImageEntity> productImageEntities) {
        this.productImageEntities = productImageEntities;
    }

    @JsonIgnore
    public Set<CategoryEntity> getCategoryEntities() {
        logger.info("getCategoryEntities");
        return categoryEntities;
    }

    public void setCategoryEntities(Set<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }

    @JsonIgnore
    public Set<FavoriteEntity> getFavoriteEntities() {
        logger.info("getFavoriteEntities");
        return favoriteEntities;
    }

    public void setFavoriteEntities(Set<FavoriteEntity> favoriteEntities) {
        this.favoriteEntities = favoriteEntities;
    }

    @JsonIgnore
    public Set<ViewEntity> getViewEntities() {
        logger.info("getViewEntities");
        return viewEntities;
    }

    public void setViewEntities(Set<ViewEntity> viewEntities) {
        this.viewEntities = viewEntities;
    }

    @JsonIgnore
    public Set<RateEntity> getRateEntities() {
        logger.info("getRateEntities");
        return rateEntities;
    }

    public void setRateEntities(Set<RateEntity> rateEntities) {
        this.rateEntities = rateEntities;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", uploadTime=" + uploadTime +
                ", productInfoEntity=" + productInfoEntity +
                ", customerEntity=" + customerEntity +
                ", productImageEntities=" + productImageEntities +
                ", categoryEntities=" + categoryEntities +
                ", favoriteEntities=" + favoriteEntities +
                ", viewEntities=" + viewEntities +
                ", rateEntities=" + rateEntities +
                '}';
    }
}
