package com.nativeboyz.vmall.models.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products_details")
public class ProductDetailsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "characteristics", length = 1000)
    private String characteristics;

    @Column(name = "keywords", length = 500)
    private String keywords;

    public ProductDetailsEntity() { }

    public ProductDetailsEntity(String description, String characteristics, String keywords) {
        this.description = description;
        this.characteristics = characteristics;
        this.keywords = keywords;
    }

    public ProductDetailsEntity(String description, String characteristics, List<String> keywords) {
        this.description = description;
        this.characteristics = characteristics;
        setKeywords(keywords);
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

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = String.join(",", keywords);
    }

    @Override
    public String toString() {
        return "ProductDetailsEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
