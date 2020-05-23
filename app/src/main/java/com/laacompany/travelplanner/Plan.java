package com.laacompany.travelplanner;

public class Plan {

    private String name, address, image_URL, arrivedTime;
    private int duration;

    public Plan(String name, String address, String image_URL, String arrivedTime, int duration) {
        this.name = name;
        this.address = address;
        this.image_URL = image_URL;
        this.arrivedTime = arrivedTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_URL() {
        return image_URL;
    }

    public void setImage_URL(String image_URL) {
        this.image_URL = image_URL;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
