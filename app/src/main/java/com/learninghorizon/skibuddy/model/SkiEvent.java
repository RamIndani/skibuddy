package com.learninghorizon.skibuddy.model;

/**
 * Created by ramnivasindani on 11/27/15.
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SkiEvent {
    private String id;
    private String eventName;
    private String eventDescription;
    private String locationName;
    private String latitude;
    private String longitude;
    private long date;
    private String startTime;
    private String endTime;
    private List<String> eventInvites = new ArrayList<>();
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public List<String> getEventInvites() {
        return eventInvites;
    }
    public void setEventInvites(List<String> eventInvites) {
        this.eventInvites = eventInvites;
    }

    public void setEventInvite(final String userId){
        eventInvites.add(userId);
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

