package com.nativeboyz.vmall.models.criteria.product;

import java.util.Arrays;

public class ProductTransformedCriteria extends ProductCriteria {

    private String[] fileNames;

    public ProductTransformedCriteria(ProductCriteria criteria, String[] fileNames) {
        super(criteria.name, criteria.price, criteria.description, criteria.details, criteria.hashTags, criteria.categories, criteria.uploaderId);
        this.fileNames = fileNames;
    }

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public String toString() {
        return "ProductTransformedCriteria{" +
                "fileNames=" + Arrays.toString(fileNames) +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", categories=" + Arrays.toString(categories) +
                ", uploaderId=" + uploaderId +
                '}';
    }
}
