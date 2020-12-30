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

    @Column(name = "action_timestamp")
    private Timestamp actionTimestamp;

    public ViewEntity() { }

    public ViewEntity(CustomerProductIdentity id) {
        this.id = id;
        this.actionTimestamp = new Timestamp(System.currentTimeMillis());
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

    @Override
    public String toString() {
        return "ViewEntity{" +
                "id=" + id +
                ", actionTimestamp=" + actionTimestamp +
                '}';
    }
}
