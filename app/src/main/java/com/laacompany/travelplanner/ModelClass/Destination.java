package com.laacompany.travelplanner.ModelClass;

public class Destination {

    private String destination_id, name, address, country, previewURL, flagURL;
    private double rating, latitude, longitude;
    private int visitor, visitorPerDay, bestTimeStart, bestTimeEnd, openTime, closeTime;

    public Destination(String destination_id, String name, String address, String country, String previewURL, String flagURL, double rating, double latitude, double longitude, int visitor, int visitorPerDay, int bestTimeStart, int bestTimeEnd, int openTime, int closeTime) {
        this.destination_id = destination_id;
        this.name = name;
        this.address = address;
        this.country = country;
        this.previewURL = previewURL;
        this.flagURL = flagURL;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visitor = visitor;
        this.visitorPerDay = visitorPerDay;
        this.bestTimeStart = bestTimeStart;
        this.bestTimeEnd = bestTimeEnd;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public int getBestTimeStart() {
        return bestTimeStart;
    }

    public void setBestTimeStart(int bestTimeStart) {
        this.bestTimeStart = bestTimeStart;
    }

    public int getBestTimeEnd() {
        return bestTimeEnd;
    }

    public void setBestTimeEnd(int bestTimeEnd) {
        this.bestTimeEnd = bestTimeEnd;
    }

    public int getVisitorPerDay() {
        return visitorPerDay;
    }

    public void setVisitorPerDay(int visitorPerDay) {
        this.visitorPerDay = visitorPerDay;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }
}
