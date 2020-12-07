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

    public ProductFileCriteria(
            @NotNull UUID ownerId,
            @NotNull @NotBlank String name,
            @NotNull Float price,
            @NotNull @NotEmpty UUID[] categories,
            @NotNull @NotBlank String description,
            String details,
            String[] hashTags,
            String[] previousFileNames,
            @NotNull @NotEmpty MultipartFile[] files
    ) {
        super(ownerId, name, price, categories, description, details, hashTags, previousFileNames);
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
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + Arrays.toString(categories) +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                ", previousFileNames=" + Arrays.toString(previousFileNames) +
                '}';
    }
}
