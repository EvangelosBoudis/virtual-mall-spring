package com.nativeboyz.vmall.models.dto;

import java.util.UUID;

public class CountDto {

    private UUID productId;
    private long count;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductCountDto{" +
                "productId=" + productId +
                ", count=" + count +
                '}';
    }
}
