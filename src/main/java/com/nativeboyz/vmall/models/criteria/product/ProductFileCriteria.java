package com.nativeboyz.vmall.models.criteria.product;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

public class ProductFileCriteria extends ProductCriteria {

    @NotNull
    @NotEmpty
    private MultipartFile[] files;

    public ProductFileCriteria(@NotNull @NotBlank String name, @NotNull Float price, @NotNull @NotBlank String description, String details, String[] hashTags, @NotNull @NotBlank UUID[] categories, @NotNull @NotBlank UUID uploaderId, @NotNull @NotEmpty MultipartFile[] files) {
        super(name, price, description, details, hashTags, categories, uploaderId);
        this.files = files;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ProductFileCriteria{" +
                "files=" + Arrays.toString(files) +
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
