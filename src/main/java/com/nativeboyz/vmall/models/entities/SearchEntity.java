package com.nativeboyz.vmall.models.entities;

import com.nativeboyz.vmall.models.entities.composeKyes.CustomerTextSearchCompositeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "searches")
@IdClass(CustomerTextSearchCompositeKey.class)
public class SearchEntity {

    private final static Logger logger = LoggerFactory.getLogger(SearchEntity.class);

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;

    @Id
    @Column(name = "text_search")
    private String textSearch;

    @Column(name = "last_search")
    private Timestamp lastSearch;

    public SearchEntity() { }

    public SearchEntity(CustomerEntity customerEntity, String textSearch, Timestamp lastSearch) {
        this.customerEntity = customerEntity;
        this.textSearch = textSearch;
        this.lastSearch = lastSearch;
    }

    public CustomerEntity getCustomerEntity() {
        logger.info("getCustomerEntity");
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
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
                "customerEntity=" + customerEntity +
                ", textSearch='" + textSearch + '\'' +
                ", lastSearch=" + lastSearch +
                '}';
    }
}
