package com.drivewell.drivewell.model;

/**
 * Created by abid on 3/26/18.
 */

public class JobsDataModel {
    private String title;
    private String description;
    private String location;
    private String job_category;
    private String user_name;
    private String user_id;
    private String user_image;
    private String time_stamp;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public JobsDataModel(String title, String description, String location, String job_category, String user_id, String user_name, String user_image, String time_stamp) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.job_category = job_category;
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_image = user_image;
        this.time_stamp = time_stamp;
    }

    public JobsDataModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJob_category() {
        return job_category;
    }

    public void setJob_category(String job_category) {
        this.job_category = job_category;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
