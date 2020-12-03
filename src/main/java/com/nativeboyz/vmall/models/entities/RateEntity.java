package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nativeboyz.vmall.models.entities.composeKyes.CustomerProductCompositeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "rates")
@IdClass(CustomerProductCompositeKey.class)
public class RateEntity {

    private final static Logger logger = LoggerFactory.getLogger(RateEntity.class);

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private CustomerEntity customerEntity;

    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductEntity productEntity;

    @Column(name = "rate_timestamp")
    private Timestamp rateTimestamp;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    public RateEntity() { }

    public RateEntity(CustomerEntity customerEntity, ProductEntity productEntity, Timestamp rateTimestamp, Integer rate) {
        this.customerEntity = customerEntity;
        this.productEntity = productEntity;
        this.rateTimestamp = rateTimestamp;
        this.rate = rate;
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

    public Timestamp getRateTimestamp() {
        return rateTimestamp;
    }

    public void setRateTimestamp(Timestamp rateTimestamp) {
        this.rateTimestamp = rateTimestamp;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "RateEntity{" +
                "customerEntity=" + customerEntity +
                ", productEntity=" + productEntity +
                ", rateTimestamp=" + rateTimestamp +
                ", rate=" + rate +
                '}';
    }
}
