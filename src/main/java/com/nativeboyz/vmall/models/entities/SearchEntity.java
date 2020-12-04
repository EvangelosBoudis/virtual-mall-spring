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

    @Column(name = "search_timestamp")
    private Timestamp searchTimestamp;

    public SearchEntity() { }

    public SearchEntity(SearchIdentity id, Timestamp searchTimestamp) {
        this.id = id;
        this.searchTimestamp = searchTimestamp;
    }

    public SearchIdentity getId() {
        return id;
    }

    public void setId(SearchIdentity id) {
        this.id = id;
    }

    public Timestamp getSearchTimestamp() {
        return searchTimestamp;
    }

    public void setSearchTimestamp(Timestamp searchTimestamp) {
        this.searchTimestamp = searchTimestamp;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "id=" + id +
                ", searchTimestamp=" + searchTimestamp +
                '}';
    }
}
