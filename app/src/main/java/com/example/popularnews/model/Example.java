package com.example.popularnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("fio")
    @Expose
    private String fio;
    @SerializedName("region")
    @Expose
    private Region region;
    @SerializedName("taklif")
    @Expose
    private String taklif;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("like_count")
    @Expose
    private String likeCount;
    @SerializedName("dislike_count")
    @Expose
    private String dislikeCount;



    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getTaklif() {
        return taklif;
    }

    public void setTaklif(String taklif) {
        this.taklif = taklif;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }



}
