package com.nativeboyz.vmall.models.dto;

import java.util.UUID;

public class RateDto {

    private UUID productId;
    private Double avg;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "RateDto{" +
                "productId=" + productId +
                ", avg=" + avg +
                '}';
    }
}
