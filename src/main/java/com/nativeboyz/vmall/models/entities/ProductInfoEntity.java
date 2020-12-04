package com.nativeboyz.vmall.models.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "products_info")
public class ProductInfoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "details", length = 1000)
    private String details;

    @Column(name = "hashTags")
    private String[] hashTags;

    public ProductInfoEntity() { }

    public ProductInfoEntity(String description, String details, String[] hashTags) {
        this.description = description;
        this.details = details;
        this.hashTags = hashTags;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String[] getHashTags() {
        return hashTags;
    }

    public void setHashTags(String[] hashTags) {
        this.hashTags = hashTags;
    }

    @Override
    public String toString() {
        return "ProductInfoEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", hashTags=" + Arrays.toString(hashTags) +
                '}';
    }
}
