package com.laacompany.travelplanner.Handle;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.ModelClass.PlanMaster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Handle {

    public static ArrayList<PlanMaster> sPlanMasters;
    public static ArrayList<Destination> sDestinations;
    public static ArrayList<Pair<Double,Double>> sCurrentRoutes;
    private static int count = 0;

    public static void init(){
        sPlanMasters = new ArrayList<>();
        sDestinations = new ArrayList<>();
        sCurrentRoutes = new ArrayList<>();
    }

    public static String getHourFormat(int minutes){
        int hour = minutes/60;
        int minute = minutes%60;
        return ((hour>9)? hour : "0"+hour) + ":" + ((minute>9)? minute : "0"+minute);
    }

    public static ArrayList<PlanMaster> getCurrentPlanMasters(Date date){

        ArrayList<PlanMaster> currentPlanMasters = new ArrayList<>();
        Calendar cPM = Calendar.getInstance();
        cPM.setTime(date);
        for(PlanMaster planMaster : sPlanMasters){
            Calendar c = Calendar.getInstance();
            c.setTime(planMaster.getEventDate());
            if(cPM.get(Calendar.YEAR) != c.get(Calendar.YEAR)) continue;
            if(cPM.get(Calendar.MONTH) != c.get(Calendar.MONTH)) continue;
            if(cPM.get(Calendar.DATE) != c.get(Calendar.DATE)) continue;

            currentPlanMasters.add(planMaster);
        }
        return currentPlanMasters;
    }


    public static String generatePlanID(){
        return "PID_" + (++count);
    }

    public static Destination getDestination(String destinationID){
        for(Destination destination : sDestinations){
            if(destination.getDestination_id().equals(destinationID)) return destination;
        }
        return null;
    }

    public static Destination getDestination(int index){
        return sDestinations.get(index);
    }


}
