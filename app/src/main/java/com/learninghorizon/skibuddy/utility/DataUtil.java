package com.learninghorizon.skibuddy.utility;

import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.model.SkiSession;
import com.learninghorizon.skibuddy.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramnivasindani on 11/27/15.
 */
public class DataUtil {
    private  User user;
    private List<SkiSession> skiSession;
    private static final DataUtil dataUtil = new DataUtil();
    private static final List<SkiEvent> totalEvents = new ArrayList<SkiEvent>();
    private static final List<User> eventAttendies = new ArrayList<User>();

    private List<User> usersList = new ArrayList<User>();

    private DataUtil(){

    }

    public static DataUtil getInstance(){
        return dataUtil;
    }

    public  User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTotalEvents(List<SkiEvent> totalEvents) {
        this.totalEvents.addAll(totalEvents);
    }

    public List<SkiEvent> getTotalEvents() {
        return totalEvents;
    }

    public void setEventAttendies(List<User> eventAttendies){
        if(null != eventAttendies && null != eventAttendies){
            this.eventAttendies.addAll(eventAttendies);
        }

    }

    public void clearEvents(){
        totalEvents.clear();
    }
    public void clearUsers() {this.usersList.clear();}
    public List<User> getEventAttendies(){
        return this.eventAttendies;
    }

    public void clearEventAttendies() {
        this.eventAttendies.clear();
    }

    public void setTotalEvent(SkiEvent totalEvent) {
        this.totalEvents.add(totalEvent);
    }

    public void clearData(){
        try {
            this.skiSession.clear();
            this.usersList.clear();
            this.totalEvents.clear();
            this.eventAttendies.clear();
        }catch(Exception exception){
            //exception.printStackTrace();
        }
    }


    public SkiSession getSkiSession(int position){
        return this.skiSession.get(position);
    }

    public void setSkiSession(List<SkiSession> skiSession) {
        this.skiSession = skiSession;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList.addAll(usersList);
    }

    public List<User> getUsersList(){
        return usersList;
    }
}
