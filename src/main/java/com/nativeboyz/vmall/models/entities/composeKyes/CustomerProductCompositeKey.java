package com.nativeboyz.vmall.models.entities.composeKyes;

import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;

import java.io.Serializable;
import java.util.Objects;

public class CustomerProductCompositeKey implements Serializable {

    private CustomerEntity customerEntity;

    private ProductEntity productEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerProductCompositeKey that = (CustomerProductCompositeKey) o;
        return customerEntity.equals(that.customerEntity) &&
                productEntity.equals(that.productEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerEntity, productEntity);
    }

}
