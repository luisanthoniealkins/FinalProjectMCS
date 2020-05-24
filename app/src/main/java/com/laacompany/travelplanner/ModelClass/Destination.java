package com.laacompany.travelplanner.ModelClass;

public class Destination {

    private String name, address, country, previewURL, flagURL;
    private float rating;
    private int visitor, visitorPerDay, bestTimeStart, bestTimeEnd, openTime, closeTime;

    public Destination(String name, String address, String country, String previewURL, String flagURL, float rating, int visitor, int visitorPerDay, int bestTimeStart, int bestTimeEnd, int openTime, int closeTime) {
        this.name = name;
        this.address = address;
        this.country = country;
        this.previewURL = previewURL;
        this.flagURL = flagURL;
        this.rating = rating;
        this.visitor = visitor;
        this.visitorPerDay = visitorPerDay;
        this.bestTimeStart = bestTimeStart;
        this.bestTimeEnd = bestTimeEnd;
        this.openTime = openTime;
        this.closeTime = closeTime;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
