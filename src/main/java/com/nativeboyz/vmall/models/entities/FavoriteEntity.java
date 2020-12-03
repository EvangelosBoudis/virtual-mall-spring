package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorites")
public class FavoriteEntity {

    private final static Logger logger = LoggerFactory.getLogger(FavoriteEntity.class);

    @EmbeddedId
    private CustomerProductIdentity id;

    @Column(name = "action_timestamp")
    private Timestamp actionTimestamp;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public FavoriteEntity() { }

    public FavoriteEntity(CustomerProductIdentity id, Timestamp actionTimestamp, Boolean status) {
        this.id = id;
        this.actionTimestamp = actionTimestamp;
        this.status = status;
    }

    public CustomerProductIdentity getId() {
        return id;
    }

    public void setId(CustomerProductIdentity id) {
        this.id = id;
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
                "id=" + id +
                ", actionTimestamp=" + actionTimestamp +
                ", status=" + status +
                '}';
    }
}
