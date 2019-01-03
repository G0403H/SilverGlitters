package com.wordpress.zeel.silverglitters;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Upload {
    private String name;
    private String imageURL;
    private String category;
    private String description;
    private String weblink;
    private String key;
    private double price;
    private ArrayList<String> otherImageURLs;

    public Upload() {
        // empty constructor is needed - don't delete
    }

    public Upload(String name, String imageURL, String category, double price) {
        this.name = name;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
        this.description = description;
        this.weblink = weblink;
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

    public String getDescription() {
        if(description==null)
            description = "No description available";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeblink() {
        if(weblink==null)
            weblink = "http://www.silverglitters.com";
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public ArrayList<String> getOtherImageURLs() {
        if (this.otherImageURLs == null)
            this.otherImageURLs = new ArrayList<>();
        return otherImageURLs;
    }

    public void setOtherImageURLs(ArrayList<String> otherImageURLs) {
        this.otherImageURLs = otherImageURLs;
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
