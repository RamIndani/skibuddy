package com.learninghorizon.skibuddy.model;

/**
 * Created by ramnivasindani on 11/27/15.
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class User {

    private String firstName;
    private String lastName;
    private String tagLine;
    private String facebookId;
    private String lastKnownLatitude;
    private String lastKnownLongitude;
    private String lastKnownCity;
    private String lastKnownState;
    private long totalRides;
    private long totalDistance;
    private long ranking;
    private long views;
    private long eventsAttended;
    private long victories;
    private boolean shareLocation;
    private boolean isSelected;
    private List<SkiEvent> createdEvents = new ArrayList<>();
    private List<SkiEvent> attendingEvents = new ArrayList<>();
    private List<SkiEvent> eventInvites = new ArrayList<>();
    private List<SkiSession> skiSessions = new ArrayList<>();
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getTagLine() {
        return tagLine;
    }
    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }
    public String getFacebookId() {
        return facebookId;
    }
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
    public String getLastKnownLatitude() {
        return lastKnownLatitude;
    }
    public void setLastKnownLatitude(String lastKnownLatitude) {
        this.lastKnownLatitude = lastKnownLatitude;
    }
    public String getLastKnownLongitude() {
        return lastKnownLongitude;
    }
    public void setLastKnownLongitude(String lastKnownLongitude) {
        this.lastKnownLongitude = lastKnownLongitude;
    }
    public String getLastKnownCity() {
        return lastKnownCity;
    }
    public void setLastKnownCity(String lastKnownCity) {
        this.lastKnownCity = lastKnownCity;
    }
    public String getLastKnownState() {
        return lastKnownState;
    }
    public void setLastKnownState(String lastKnownState) {
        this.lastKnownState = lastKnownState;
    }
    public long getTotalRides() {
        return totalRides;
    }
    public void setTotalRides(long totalRides) {
        this.totalRides = totalRides;
    }
    public long getTotalDistance() {
        return totalDistance;
    }
    public void setTotalDistance(long totalDistance) {
        this.totalDistance = totalDistance;
    }
    public long getRanking() {
        return ranking;
    }
    public void setRanking(long ranking) {
        this.ranking = ranking;
    }
    public long getViews() {
        return views;
    }
    public void setViews(long views) {
        this.views = views;
    }
    public long getEventsAttended() {
        return eventsAttended;
    }
    public void setEventsAttended(long eventsAttended) {
        this.eventsAttended = eventsAttended;
    }
    public long getVictories() {
        return victories;
    }
    public void setVictories(long victories) {
        this.victories = victories;
    }
    public List<SkiEvent> getCreatedEvents() {
        return createdEvents;
    }
    public void setCreatedEvents(List<SkiEvent> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public void setCreatedEvent(SkiEvent skiEvent){
        this.createdEvents.add(skiEvent);
    }
    public List<SkiEvent> getAttendingEvents() {
        return attendingEvents;
    }
    public void setAttendingEvents(List<SkiEvent> attendingEvents) {
        this.attendingEvents = attendingEvents;
    }

    public void setAttendingEvent(SkiEvent attendingEvent){
        this.attendingEvents.add(attendingEvent);
    }
    public List<SkiEvent> getEventInvites() {
        return eventInvites;
    }
    public void setEventInvites(List<SkiEvent> eventInvites) {
        this.eventInvites = eventInvites;
    }

    public void setEventInvite(SkiEvent eventInvite){
        this.eventInvites.add(eventInvite);
    }
    public List<SkiSession> getSkiSessions() {
        return skiSessions;
    }
    public void setSkiSessions(List<SkiSession> skiSessions) {
        this.skiSessions = skiSessions;
    }
    public void setSkiSession(SkiSession skiSession){
        this.skiSessions.add(skiSession);
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isShareLocation() {
        return shareLocation;
    }
    public void setShareLocation(boolean shareLocation) {
        this.shareLocation = shareLocation;
    }
}

