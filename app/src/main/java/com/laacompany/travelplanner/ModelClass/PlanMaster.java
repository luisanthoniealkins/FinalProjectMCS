package com.laacompany.travelplanner.ModelClass;

import java.util.ArrayList;
import java.util.Date;

public class PlanMaster {

    private String plan_id,eventTitle;
    private Date eventDate;
    private int timeStart;
    private double latitude, longitude;
    private ArrayList<Plan> plans;

    public PlanMaster(String plan_id){
        this.plan_id = plan_id;
    }

    public PlanMaster(String plan_id, String eventTitle, Date eventDate, int timeStart, double latitude, double longitude) {
        this.plan_id = plan_id;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.timeStart = timeStart;
        this.latitude = latitude;
        this.longitude = longitude;
        plans = new ArrayList<>();
    }

    public PlanMaster(){
        plans = new ArrayList<>();
    }

    public void setPlanMaster(PlanMaster planMaster) {
        this.plan_id = planMaster.getPlan_id();
        this.eventTitle = planMaster.getEventTitle();
        this.eventDate = planMaster.getEventDate();
        this.timeStart = planMaster.getTimeStart();
        this.latitude = planMaster.getLatitude();
        this.longitude = planMaster.getLongitude();
        this.plans = planMaster.getPlans();
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
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

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
    }
}
