package com.laacompany.travelplanner.ModelClass;

public class Plan {

    private String name, address, image_URL;
    private int arrivedTime, duration;

    public Plan(String name, String address, String image_URL, int arrivedTime, int duration) {
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

    public int getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(int arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
