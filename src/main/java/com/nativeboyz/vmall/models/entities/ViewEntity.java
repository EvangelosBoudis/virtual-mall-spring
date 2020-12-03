package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nativeboyz.vmall.models.entities.composeKyes.CustomerProductCompositeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "views")
@IdClass(CustomerProductCompositeKey.class)
public class ViewEntity {

    private final static Logger logger = LoggerFactory.getLogger(ViewEntity.class);

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

    @Column(name = "last_view")
    private Timestamp lastView;

    public ViewEntity() { }

    public ViewEntity(CustomerEntity customerEntity, ProductEntity productEntity, Timestamp lastView) {
        this.customerEntity = customerEntity;
        this.productEntity = productEntity;
        this.lastView = lastView;
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

    public Timestamp getLastView() {
        return lastView;
    }

    public void setLastView(Timestamp lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return "ViewEntity{" +
                "customerEntity=" + customerEntity +
                ", productEntity=" + productEntity +
                ", lastView=" + lastView +
                '}';
    }
}
