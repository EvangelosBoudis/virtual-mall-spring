package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
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

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "details", length = 1000)
    private String details;

    @Column(name = "hashTags")
    private String[] hashTags;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private CustomerEntity customerEntity;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ProductImageEntity> productImageEntities;

    @ManyToMany()
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"), // product id
            inverseJoinColumns = @JoinColumn(name = "category_id") // category id
    )
    @JsonIgnore
    private Set<CategoryEntity> categoryEntities;

    @OneToMany(mappedBy = "id.productId")
    @JsonIgnore
    private Set<FavoriteEntity> favoriteEntities;

    @OneToMany(mappedBy = "id.productId")
    @JsonIgnore
    private Set<ViewEntity> viewEntities;

    @OneToMany(mappedBy = "id.productId")
    @JsonIgnore
    private Set<RateEntity> rateEntities;

    public ProductEntity() { }

    public ProductEntity(String name, Float price, String description, String details, String[] hashTags) {
        this.name = name;
        this.price = price;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.description = description;
        this.details = details;
        this.hashTags = hashTags;
    }

    public static Logger getLogger() {
        return logger;
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

    public CustomerEntity getCustomerEntity() {
        logger.info("getCustomerEntity");
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Set<ProductImageEntity> getProductImageEntities() {
        logger.info("getProductImageEntities");
        return productImageEntities;
    }

    public void setProductImageEntities(Set<ProductImageEntity> productImageEntities) {
        this.productImageEntities = productImageEntities;
    }

    public Set<CategoryEntity> getCategoryEntities() {
        logger.info("getCategoryEntities");
        return categoryEntities;
    }

    public void setCategoryEntities(Set<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }

    public Set<FavoriteEntity> getFavoriteEntities() {
        logger.info("getFavoriteEntities");
        return favoriteEntities;
    }

    public void setFavoriteEntities(Set<FavoriteEntity> favoriteEntities) {
        this.favoriteEntities = favoriteEntities;
    }

    public Set<ViewEntity> getViewEntities() {
        logger.info("getViewEntities");
        return viewEntities;
    }

    public void setViewEntities(Set<ViewEntity> viewEntities) {
        this.viewEntities = viewEntities;
    }

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
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", customerEntity=" + customerEntity +
                ", productImageEntities=" + productImageEntities +
                ", categoryEntities=" + categoryEntities +
                ", favoriteEntities=" + favoriteEntities +
                ", viewEntities=" + viewEntities +
                ", rateEntities=" + rateEntities +
                '}';
    }
}
