package com.drivewell.drivewell.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by abid on 1/19/18.
 */

public class DriverModel implements Comparable<DriverModel> {
    private String firstname;
    private String lastname;
    private int points;

    public DriverModel() {
    }

    public DriverModel(String firstname, String lastname, int points) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.points = points;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString() {
        return "DriverModel{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public int compareTo(@NonNull DriverModel driverModel) {
        return driverModel.getPoints()-this.getPoints();
    }

    public static Comparator<DriverModel> driverModelComparator
            = new Comparator<DriverModel>() {

        @Override
        public int compare(DriverModel driverModel, DriverModel t1) {
            return driverModel.compareTo(t1);
        }


    };
}
