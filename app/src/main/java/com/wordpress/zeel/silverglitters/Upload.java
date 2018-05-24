package com.wordpress.zeel.silverglitters;

import com.google.firebase.database.Exclude;

public class Upload {
    private String name;
    private String imageURL;
    private String category;
    private String key;
    private double price;

    public Upload() {
        // empty constructor is needed - don't delete
    }

    public Upload(String name, String imageURL, String category, double price) {
        this.name = name;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
