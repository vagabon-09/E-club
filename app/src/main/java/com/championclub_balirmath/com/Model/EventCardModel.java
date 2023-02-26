package com.championclub_balirmath.com.Model;

public class EventCardModel {
    private String EventName;
    private String EventOrganiserName;
    private long EventDate;
    private boolean alarm;

    public EventCardModel() {
    }

    public EventCardModel(String eventName, String eventOrganiserName, long eventDate, boolean alarm) {
        EventName = eventName;
        EventOrganiserName = eventOrganiserName;
        EventDate = eventDate;
        this.alarm = alarm;
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

    public long getEventDate() {
        return EventDate;
    }

    public void setEventDate(long eventDate) {
        EventDate = eventDate;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

}
