package com.example.dateyoureve;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MyHomeData {
    private String title,description,date,venue,mode,notes,paid,time,userId,eventId,phone;
    private String image;



    public MyHomeData(){}

    public MyHomeData(String title, String description, String date, String venue, String mode, String notes, String paid, String time, String userId, String eventId, String image,String phone) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.venue = venue;
        this.mode = mode;
        this.notes = notes;
        this.paid = paid;
        this.time = time;
        this.userId = userId;
        this.image = image;
        this.eventId = eventId;
        this.phone = phone;
    }

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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
