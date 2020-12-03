package com.nativeboyz.vmall.models.entities.identities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SearchIdentity implements Serializable {

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "text_search")
    private String textSearch;

    public SearchIdentity() { }

    public SearchIdentity(UUID customerId, String textSearch) {
        this.customerId = customerId;
        this.textSearch = textSearch;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchIdentity that = (SearchIdentity) o;
        return customerId.equals(that.customerId) &&
                textSearch.equals(that.textSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, textSearch);
    }

    @Override
    public String toString() {
        return "SearchIdentity{" +
                "customerId=" + customerId +
                ", textSearch='" + textSearch + '\'' +
                '}';
    }
}
