package com.nativeboyz.vmall.models.dto;

public class ProductAdditionalInfo {

    private Integer viewsQty;
    private Integer favoritesQty;
    private Float avgRate;
    private Boolean favoriteByCustomer;

    public ProductAdditionalInfo(Integer viewsQty, Integer favoritesQty, Float avgRate, Boolean favoriteByCustomer) {
        this.viewsQty = viewsQty;
        this.favoritesQty = favoritesQty;
        this.avgRate = avgRate;
        this.favoriteByCustomer = favoriteByCustomer;
    }

    public Integer getViewsQty() {
        return viewsQty;
    }

    public void setViewsQty(Integer viewsQty) {
        this.viewsQty = viewsQty;
    }

    public Integer getFavoritesQty() {
        return favoritesQty;
    }

    public void setFavoritesQty(Integer favoritesQty) {
        this.favoritesQty = favoritesQty;
    }

    public Float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Float avgRate) {
        this.avgRate = avgRate;
    }

    public Boolean getFavoriteByCustomer() {
        return favoriteByCustomer;
    }

    public void setFavoriteByCustomer(Boolean favoriteByCustomer) {
        this.favoriteByCustomer = favoriteByCustomer;
    }

    @Override
    public String toString() {
        return "ProductAdditionalInfo{" +
                "viewsQty=" + viewsQty +
                ", favoritesQty=" + favoritesQty +
                ", avgRate=" + avgRate +
                ", favoriteByCustomer=" + favoriteByCustomer +
                '}';
    }
}
