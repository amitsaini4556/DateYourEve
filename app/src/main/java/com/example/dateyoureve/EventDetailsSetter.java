package com.example.dateyoureve;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventDetailsSetter {
    String title,description,notes,mode,date,image,time,userId,venue,eventId,phone;
    public  EventDetailsSetter(){

    }

    public EventDetailsSetter(String title, String description, String notes, String mode, String paid, String image, String date, String time,String venue, String userId, String eventId, String phone) {
        this.title = title;
        this.description = description;
        this.notes = notes;
        this.mode = mode;
        this.image = image;
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.venue = venue;
        this.eventId = eventId;
        this.phone = phone;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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
