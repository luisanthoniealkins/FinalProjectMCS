package com.laacompany.travelplanner;

import com.laacompany.travelplanner.ModelClass.Plan;

import java.util.ArrayList;

public class Handle {

    public static ArrayList<Plan> sPLans;

    public static String getHourFormat(int minutes){
        int hour = minutes/60;
        int minute = minutes%60;
        return ((hour>9)? hour : "0"+hour) + ":" + ((minute>9)? minute : "0"+minute);
    }


}
