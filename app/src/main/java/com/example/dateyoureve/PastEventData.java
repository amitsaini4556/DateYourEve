package com.example.dateyoureve;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PastEventData {

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public PastEventData(){

    }
    public PastEventData(String title, String date, long interested_count,String eventId) {
        this.title = title;
        this.date = date;
        this.interested_count = interested_count;
        this.eventId=eventId;

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

    String title,date,eventId;
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
