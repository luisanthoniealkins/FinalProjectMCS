package com.laacompany.travelplanner.ModelClass;

import java.util.ArrayList;
import java.util.Date;

public class PlanMaster {

    private String masterPlanId,eventTitle;
    private Date eventDate;
    private int timeStart;
    private double latitude, longitude;
    private ArrayList<Plan> plans;

    public PlanMaster(String masterPlanId){
        this.masterPlanId = masterPlanId;
    }

    public PlanMaster(String masterPlanId, String eventTitle, Date eventDate, int timeStart, double latitude, double longitude) {
        this.masterPlanId = masterPlanId;
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
        this.masterPlanId = planMaster.getMasterPlanId();
        this.eventTitle = planMaster.getEventTitle();
        this.eventDate = planMaster.getEventDate();
        this.timeStart = planMaster.getTimeStart();
        this.latitude = planMaster.getLatitude();
        this.longitude = planMaster.getLongitude();
        this.plans = planMaster.getPlans();
    }

    public String getMasterPlanId() {
        return masterPlanId;
    }

    public void setMasterPlanId(String masterPlanId) {
        this.masterPlanId = masterPlanId;
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
