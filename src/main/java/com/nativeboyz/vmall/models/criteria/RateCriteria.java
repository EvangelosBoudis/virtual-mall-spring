package com.nativeboyz.vmall.models.criteria;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RateCriteria {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID productId;

    private int rate;

    private String comment;

    public RateCriteria(@NotNull UUID customerId, @NotNull UUID productId, int rate, String comment) {
        this.customerId = customerId;
        this.productId = productId;
        this.rate = rate;
        this.comment = comment;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "RateCriteria{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
