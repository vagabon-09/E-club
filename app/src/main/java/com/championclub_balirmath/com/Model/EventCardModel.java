package com.championclub_balirmath.com.Model;

public class EventCardModel {
    private String EventName;
    private String EventOrganiserName;
    private String EventDate;

    public EventCardModel() {
    }

    public EventCardModel(String eventName, String eventOrganiserName, String eventDate) {
        EventName = eventName;
        EventOrganiserName = eventOrganiserName;
        EventDate = eventDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventOrganiserName() {
        return EventOrganiserName;
    }

    public void setEventOrganiserName(String eventOrganiserName) {
        EventOrganiserName = eventOrganiserName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }
}
