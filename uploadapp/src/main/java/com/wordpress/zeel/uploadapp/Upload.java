package com.wordpress.zeel.uploadapp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Upload {
    private String name;
    private String imageURL;
    private String category;
    private String key;
    private double price;
    private String description;
    private String weblink;
    private ArrayList<String> otherImageURLs;

    public Upload() {

    }

    public Upload(String name, String imageURL, String category, String price) {
        this.name = name;
        this.imageURL = imageURL;
        this.category = category;
        this.price = Double.parseDouble(price);
    }

    public Upload(String name, String imageURL, String category, String price, String description, String weblink) {
        this.name = name;
        this.imageURL = imageURL;
        this.category = category;
        this.price = Double.parseDouble(price);
        this.description = description;
        this.weblink = weblink;
        this.otherImageURLs = new ArrayList<>();
    }

    public ArrayList<String> getOtherImageURLs() {
        if(this.otherImageURLs==null){
            this.otherImageURLs = new ArrayList<>();
        }
        return otherImageURLs;
    }

    public void setOtherImageURLs(ArrayList<String> otherImageURLs) {
        this.otherImageURLs = otherImageURLs;
    }

    public void addUrl(String urls) {
        if(this.otherImageURLs == null)
            this.otherImageURLs = new ArrayList<>();
        this.otherImageURLs.add(urls);
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

    public String getDescription() {
        if (description == null)
            description = "No description available.";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeblink() {
        if (weblink == null)
            weblink = "Link for the website";
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}