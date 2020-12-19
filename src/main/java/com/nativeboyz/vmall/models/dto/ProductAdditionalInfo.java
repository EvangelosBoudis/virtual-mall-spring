package com.nativeboyz.vmall.models.dto;

public class ProductAdditionalInfo {

    private Integer views;
    private Integer favorites;
    private Float avgRate;
    private Boolean requesterFavorite;

    public ProductAdditionalInfo(Integer views, Integer favorites, Float avgRate, Boolean requesterFavorite) {
        this.views = views;
        this.favorites = favorites;
        this.avgRate = avgRate;
        this.requesterFavorite = requesterFavorite;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public Float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Float avgRate) {
        this.avgRate = avgRate;
    }

    public Boolean getRequesterFavorite() {
        return requesterFavorite;
    }

    public void setRequesterFavorite(Boolean requesterFavorite) {
        this.requesterFavorite = requesterFavorite;
    }

    @Override
    public String toString() {
        return "ProductAdditionalInfo{" +
                "views=" + views +
                ", favorites=" + favorites +
                ", avgRate=" + avgRate +
                ", requesterFavorite=" + requesterFavorite +
                '}';
    }
}
