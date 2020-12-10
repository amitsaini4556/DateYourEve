package com.example.dateyoureve;

import android.net.Uri;

public class MyHomeData {
    private String title,description,date,venue;
    private String image;

    public MyHomeData(){}
    public MyHomeData(String title, String description, String date, String venue, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.venue = venue;
        this.image = image;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
