package com.example.cityguide.model.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class famousPlaceModel {
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("location")
    @Expose
    public String location;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("image")
    @Expose
    public String image;

    public famousPlaceModel(String name, String location, String description, String image) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
