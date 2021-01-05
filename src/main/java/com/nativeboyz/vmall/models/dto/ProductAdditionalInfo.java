package com.nativeboyz.vmall.models.dto;

public class ProductAdditionalInfo {

    private long views;
    private long favorites;
    private Double avgRate;
    private Boolean requesterFavorite;

    public ProductAdditionalInfo(long views, long favorites, Double avgRate, Boolean requesterFavorite) {
        this.views = views;
        this.favorites = favorites;
        this.avgRate = avgRate;
        this.requesterFavorite = requesterFavorite;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getFavorites() {
        return favorites;
    }

    public void setFavorites(long favorites) {
        this.favorites = favorites;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
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
