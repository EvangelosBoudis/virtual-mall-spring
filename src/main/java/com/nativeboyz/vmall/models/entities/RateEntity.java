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

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "action_timestamp")
    private Timestamp actionTimestamp;

    public RateEntity() { }

    public RateEntity(CustomerProductIdentity id, Integer rate, String comment, Timestamp actionTimestamp) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.actionTimestamp = actionTimestamp;
    }

    @Override
    public CustomerProductIdentity getId() {
        return id;
    }

    public void setId(CustomerProductIdentity id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(Timestamp actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }

    @Override
    public String toString() {
        return "RateEntity{" +
                "id=" + id +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", actionTimestamp=" + actionTimestamp +
                '}';
    }
}
