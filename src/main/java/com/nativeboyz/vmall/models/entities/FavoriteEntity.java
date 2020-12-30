package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorites")
public class FavoriteEntity implements CustomerProductEntity {

    private final static Logger logger = LoggerFactory.getLogger(FavoriteEntity.class);

    @EmbeddedId
    private CustomerProductIdentity id;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "action_timestamp")
    private Timestamp actionTimestamp;

    public FavoriteEntity() { }

    public FavoriteEntity(CustomerProductIdentity id, Boolean status) {
        this.id = id;
        this.status = status;
        this.actionTimestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public CustomerProductIdentity getId() {
        return id;
    }

    public void setId(CustomerProductIdentity id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Timestamp getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(Timestamp actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }

    @Override
    public String toString() {
        return "FavoriteEntity{" +
                "id=" + id +
                ", status=" + status +
                ", actionTimestamp=" + actionTimestamp +
                '}';
    }
}
