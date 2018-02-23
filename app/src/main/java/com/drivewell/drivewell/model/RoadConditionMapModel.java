package com.drivewell.drivewell.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abid on 1/30/18.
 */

public class RoadConditionMapModel {
    private LatLng startingLocation;
    private LatLng endLocation;
    private String condition;

    public RoadConditionMapModel(LatLng startingLocation, LatLng endLocation, String condition) {
        this.startingLocation = startingLocation;
        this.endLocation = endLocation;
        this.condition = condition;
    }

    public RoadConditionMapModel() {
    }

    public LatLng getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(LatLng startingLocation) {
        this.startingLocation = startingLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
