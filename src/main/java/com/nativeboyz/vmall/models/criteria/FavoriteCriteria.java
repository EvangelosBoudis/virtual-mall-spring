package com.nativeboyz.vmall.models.criteria;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class FavoriteCriteria {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID productId;

    private boolean status;

    public FavoriteCriteria(@NotNull UUID customerId, @NotNull UUID productId, boolean status) {
        this.customerId = customerId;
        this.productId = productId;
        this.status = status;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FavoriteCriteria{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                ", status=" + status +
                '}';
    }
}
