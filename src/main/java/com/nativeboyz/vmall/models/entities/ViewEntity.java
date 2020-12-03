package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "views")
public class ViewEntity {

    private final static Logger logger = LoggerFactory.getLogger(ViewEntity.class);

    @EmbeddedId
    private CustomerProductIdentity id;

    @Column(name = "last_view")
    private Timestamp lastView;

    public ViewEntity() { }

    public ViewEntity(CustomerProductIdentity id, Timestamp lastView) {
        this.id = id;
        this.lastView = lastView;
    }

    public CustomerProductIdentity getId() {
        return id;
    }

    public void setId(CustomerProductIdentity id) {
        this.id = id;
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
                "id=" + id +
                ", lastView=" + lastView +
                '}';
    }
}
