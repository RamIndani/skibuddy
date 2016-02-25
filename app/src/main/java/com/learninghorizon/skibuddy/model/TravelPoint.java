package com.learninghorizon.skibuddy.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by ramnivasindani on 11/27/15.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class TravelPoint {
    private String latitude;
    private String longitude;

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

    public TravelPoint(){

    }

    public TravelPoint(final String latitude, final String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

