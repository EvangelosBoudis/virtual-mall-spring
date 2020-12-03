package com.nativeboyz.vmall.models.entities.identities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CustomerProductIdentity implements Serializable {

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "product_id")
    private UUID productId;

    public CustomerProductIdentity() { }

    public CustomerProductIdentity(UUID customerId, UUID productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerProductIdentity that = (CustomerProductIdentity) o;
        return customerId.equals(that.customerId) &&
                productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productId);
    }

    @Override
    public String toString() {
        return "CustomerProductIdentity{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                '}';
    }
}
