package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.entities.identities.SearchIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "searches")
public class SearchEntity {

    private final static Logger logger = LoggerFactory.getLogger(SearchEntity.class);

    @EmbeddedId
    private SearchIdentity id;

    @Column(name = "action_timestamp")
    private Timestamp actionTimestamp;

    public SearchEntity() { }

    public SearchEntity(SearchIdentity id, Timestamp actionTimestamp) {
        this.id = id;
        this.actionTimestamp = actionTimestamp;
    }

    public SearchIdentity getId() {
        return id;
    }

    public void setId(SearchIdentity id) {
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
        return "SearchEntity{" +
                "id=" + id +
                ", actionTimestamp=" + actionTimestamp +
                '}';
    }
}
