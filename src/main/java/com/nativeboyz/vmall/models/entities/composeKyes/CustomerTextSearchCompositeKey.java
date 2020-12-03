package com.nativeboyz.vmall.models.entities.composeKyes;

import com.nativeboyz.vmall.models.entities.CustomerEntity;

import java.io.Serializable;
import java.util.Objects;

public class CustomerTextSearchCompositeKey implements Serializable {

    private CustomerEntity customerEntity;

    private String textSearch;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerTextSearchCompositeKey that = (CustomerTextSearchCompositeKey) o;
        return customerEntity.equals(that.customerEntity) &&
                textSearch.equals(that.textSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerEntity, textSearch);
    }

}
