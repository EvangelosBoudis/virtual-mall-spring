package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "rates")
public class RateEntity implements CustomerProductEntity {

    private final static Logger logger = LoggerFactory.getLogger(RateEntity.class);

    @EmbeddedId
    private CustomerProductIdentity id;

    @Column(name = "rate_timestamp")
    private Timestamp rateTimestamp;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    public RateEntity() { }

    public RateEntity(CustomerProductIdentity id, Timestamp rateTimestamp, Integer rate) {
        this.id = id;
        this.rateTimestamp = rateTimestamp;
        this.rate = rate;
    }

    public CustomerProductIdentity getId() {
        return id;
    }

    public void setId(CustomerProductIdentity id) {
        this.id = id;
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
                "id=" + id +
                ", rateTimestamp=" + rateTimestamp +
                ", rate=" + rate +
                '}';
    }
}
