package com.nativeboyz.vmall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    private final static Logger logger = LoggerFactory.getLogger(CustomerEntity.class);

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstname;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastname;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "image_name", length = 200)
    private String imageName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "date_joined")
    private Timestamp dateJoined;

    @OneToMany(mappedBy="customerEntity", fetch = FetchType.LAZY)
    private Set<ProductEntity> productEntities;

    @OneToMany(mappedBy = "id.customerId", fetch = FetchType.LAZY)
    private Set<FavoriteEntity> favoriteEntities;

    @OneToMany(mappedBy = "id.customerId", fetch = FetchType.LAZY)
    private Set<SearchEntity> searchEntities;

    @OneToMany(mappedBy = "id.customerId", fetch = FetchType.LAZY)
    private Set<ViewEntity> viewEntities;

    public CustomerEntity() { }

    public CustomerEntity(String firstname, String lastname, String email, String imageName, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.imageName = imageName;
        this.phoneNumber = phoneNumber;
        dateJoined = new Timestamp(System.currentTimeMillis());
    }

    public void update(
            String firstname, String lastname,
            String email, String phoneNumber,
            String profileImageName
    ) {
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setImageName(profileImageName);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Timestamp dateJoined) {
        this.dateJoined = dateJoined;
    }

    @JsonBackReference
    public Set<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Set<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    @JsonIgnore
    public Set<FavoriteEntity> getFavoriteEntities() {
        return favoriteEntities;
    }

    public void setFavoriteEntities(Set<FavoriteEntity> favoriteEntities) {
        this.favoriteEntities = favoriteEntities;
    }

    @JsonIgnore
    public Set<SearchEntity> getSearchEntities() {
        return searchEntities;
    }

    public void setSearchEntities(Set<SearchEntity> searchEntities) {
        this.searchEntities = searchEntities;
    }

    @JsonIgnore
    public Set<ViewEntity> getViewEntities() {
        return viewEntities;
    }

    public void setViewEntities(Set<ViewEntity> viewEntities) {
        this.viewEntities = viewEntities;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", imageName='" + imageName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateJoined=" + dateJoined +
                ", productEntities=" + productEntities +
                ", favoriteEntities=" + favoriteEntities +
                ", searchEntities=" + searchEntities +
                ", viewEntities=" + viewEntities +
                '}';
    }
}
