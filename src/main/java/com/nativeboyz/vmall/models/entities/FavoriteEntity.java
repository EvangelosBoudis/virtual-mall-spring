package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nativeboyz.vmall.models.entities.composeKyes.CustomerProductCompositeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorites")
@IdClass(CustomerProductCompositeKey.class)
public class FavoriteEntity {

    private final static Logger logger = LoggerFactory.getLogger(FavoriteEntity.class);

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id") // TODO: Check Error
    @JsonManagedReference
    private CustomerEntity customerEntity;

    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductEntity productEntity;

    @Column(name = "action_timestamp")
    private Timestamp actionTimestamp;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public FavoriteEntity() { }

    public FavoriteEntity(CustomerEntity customer, ProductEntity productEntity, Timestamp actionTimestamp, Boolean status) {
        this.customerEntity = customer;
        this.productEntity = productEntity;
        this.actionTimestamp = actionTimestamp;
        this.status = status;
    }

    public CustomerEntity getCustomerEntity() {
        logger.info("getCustomerEntity");
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public ProductEntity getProductEntity() {
        logger.info("getProductEntity");
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public Timestamp getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(Timestamp actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FavoriteEntity{" +
                "customerEntity=" + customerEntity +
                ", productEntity=" + productEntity +
                ", actionTimestamp=" + actionTimestamp +
                ", status=" + status +
                '}';
    }
}
