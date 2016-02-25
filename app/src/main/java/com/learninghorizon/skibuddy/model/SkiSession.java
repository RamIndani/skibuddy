package com.learninghorizon.skibuddy.model;

/**
 * Created by ramnivasindani on 11/27/15.
 */


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SkiSession {
    private String sessionId;
    private String sessionName;
    private String maxSpeed;
    private String averageSpeed;
    private String altitude;
    private String distance;
    private String slope;
    private String vertical;
    private long date;
    private String duration;
    private List<TravelPoint> travelPoints = new ArrayList<TravelPoint>();
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getSessionName() {
        return sessionName;
    }
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    public String getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public String getAverageSpeed() {
        return averageSpeed;
    }
    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
    public String getAltitude() {
        return altitude;
    }
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getSlope() {
        return slope;
    }
    public void setSlope(String slope) {
        this.slope = slope;
    }
    public String getVertical() {
        return vertical;
    }
    public void setVertical(String vertical) {
        this.vertical = vertical;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public List<TravelPoint> getTravelPoints() {
        return travelPoints;
    }
    public void setTravelPoints(List<TravelPoint> travelPoints) {
        this.travelPoints = travelPoints;
    }

    public void setTravelPoint(final TravelPoint travelPoint){
        this.travelPoints.add(travelPoint);
    }
}

