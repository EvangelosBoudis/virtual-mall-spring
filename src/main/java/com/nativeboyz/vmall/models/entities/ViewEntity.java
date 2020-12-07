package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.CustomerProductEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "views")
public class ViewEntity implements CustomerProductEntity {

    private final static Logger logger = LoggerFactory.getLogger(ViewEntity.class);

    @EmbeddedId
    private CustomerProductIdentity id;

    @Column(name = "view_timestamp")
    private Timestamp viewTimestamp;

    public ViewEntity() { }

    public ViewEntity(CustomerProductIdentity id, Timestamp viewTimestamp) {
        this.id = id;
        this.viewTimestamp = viewTimestamp;
    }

    public CustomerProductIdentity getId() {
        return id;
    }

    public void setId(CustomerProductIdentity id) {
        this.id = id;
    }

    public Timestamp getViewTimestamp() {
        return viewTimestamp;
    }

    public void setViewTimestamp(Timestamp viewTimestamp) {
        this.viewTimestamp = viewTimestamp;
    }

    @Override
    public String toString() {
        return "ViewEntity{" +
                "id=" + id +
                ", viewTimestamp=" + viewTimestamp +
                '}';
    }
}
