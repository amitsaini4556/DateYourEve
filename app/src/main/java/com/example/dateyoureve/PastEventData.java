package com.example.dateyoureve;

public class PastEventData {
    public PastEventData(String title, String date, String interested_count) {
        this.title = title;
        this.date = date;
        this.interested_count = interested_count;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getInterested_count() {
        return interested_count;
    }

    String title,date;
    String interested_count;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInterested_count(String interested_count) {
        this.interested_count = interested_count;
    }
}
