package com.learninghorizon.skibuddy.model;

import android.location.Location;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ramnivasindani on 11/24/15.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SessionInfo {
    private String name;
    private String sessionId;
    private String maxSpeed;
    private String averageSpeed;
    private String altitude;
    private String distance;
    private String slope;
    private String skivertical;
    private long sessionDate;
    private String duration;
    private ArrayList<Location> travelPoints = new ArrayList<>();

    public SessionInfo(final String name, final String sessionId, final String maxSpeed, final String averageSpeed,
                       final String altitude, final String distance, final String slope,
                       final String skivertical, final long sessionDate, final String duration){
        this.name = name;
        this.sessionId = sessionId;
        this.maxSpeed = maxSpeed;
        this.averageSpeed = averageSpeed;
        this.altitude = altitude;
        this.distance = distance;
        this.slope = slope;
        this.skivertical = skivertical;
        this.sessionDate = sessionDate;
        this.duration = duration;
    }

    public SessionInfo(final String name, final String sessionId, final String maxSpeed, final String averageSpeed,
                       final String altitude, final String distance, final String slope,
                       final String skivertical, final long sessionDate, final String duration,
                       final ArrayList<Location> travelPoints){
        this.name = name;
        this.sessionId = sessionId;
        this.maxSpeed = maxSpeed;
        this.averageSpeed = averageSpeed;
        this.altitude = altitude;
        this.distance = distance;
        this.slope = slope;
        this.skivertical = skivertical;
        this.sessionDate = sessionDate;
        this.duration = duration;
        this.travelPoints = travelPoints;

    }

    public SessionInfo(){

    }
    public static final String NAME_PREFIX = "Session Name";


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSkivertical() {
        return skivertical;
    }

    public void setSkivertical(String skivertical) {
        this.skivertical = skivertical;
    }

    public long getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(long sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<Location> getTravelPoints() {
        return travelPoints;
    }

    public void setTravelPoints(ArrayList<Location> travelPoints) {
        this.travelPoints = travelPoints;
    }

    public void setTravelPoints(Location location){
        travelPoints.add(location);
    }

}