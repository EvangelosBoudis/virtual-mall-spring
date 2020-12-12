package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    private final static Logger logger = LoggerFactory.getLogger(CategoryEntity.class);

    /*
    * name: function name
    * strategy: implementation of function
    * jpa requests from UUID implementation (hibernate)
    * */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "title", length = 100, nullable = false, unique = true)
    private String title;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "image_name", length = 100, nullable = false, unique = true)
    private String imageName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> productEntities;

    public CategoryEntity() { }

    public CategoryEntity(String title, String description, String imageName) {
        this.title = title;
        this.description = description;
        this.imageName = imageName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @JsonBackReference
    public Set<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Set<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", productEntities=" + productEntities +
                '}';
    }
}
