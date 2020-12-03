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

    @Column(name = "last_search")
    private Timestamp lastSearch;

    public SearchEntity() { }

    public SearchEntity(SearchIdentity id, Timestamp lastSearch) {
        this.id = id;
        this.lastSearch = lastSearch;
    }

    public SearchIdentity getId() {
        return id;
    }

    public void setId(SearchIdentity id) {
        this.id = id;
    }

    public Timestamp getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(Timestamp lastSearch) {
        this.lastSearch = lastSearch;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "id=" + id +
                ", lastSearch=" + lastSearch +
                '}';
    }
}
