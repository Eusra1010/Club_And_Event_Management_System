package com.example.eventmanagement;

public class Event {

    private String eventId;
    private String eventName;
    private String eventDate;
    private String venue;
    private String clubName;

    public Event() {
        // Required for Firebase
    }

    public Event(String eventId, String eventName, String eventDate,
                 String venue, String clubName) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.clubName = clubName;
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
}
