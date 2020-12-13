package com.example.dateyoureve;

public class PastEventData {

    public PastEventData(){

    }
    public PastEventData(String title, String date, long interested_count) {
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

    public long getInterested_count() {
        return interested_count;
    }

    String title,date;
    long interested_count;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInterested_count(long interested_count) {
        this.interested_count = interested_count;
    }
}
