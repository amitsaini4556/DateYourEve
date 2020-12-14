package com.example.dateyoureve;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PastEventData {

    String title,date,eventId,interested_count;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public PastEventData(){

    }
    public PastEventData(String title, String date, String interested_count,String eventId) {
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

    public String getInterested_count() {
        return interested_count;
    }

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
