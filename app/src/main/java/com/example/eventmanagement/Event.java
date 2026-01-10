package com.example.eventmanagement;

public class Event {

    private String eventId;
    private String eventName;
    private String eventDate;
    private String venue;
    private String clubName;
    private String fees;

    private String status;
    private boolean registrationOpen;
    private int registrationCount;

    public Event() {
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public String getClubName() {
        return clubName;
    }

    public String getStatus() {
        return status;
    }

    public boolean isRegistrationOpen() {
        return registrationOpen;
    }

    public int getRegistrationCount() {
        return registrationCount;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public String getFees() {
        return fees;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    public void setRegistrationCount(int registrationCount) {
        this.registrationCount = registrationCount;
    }
}
