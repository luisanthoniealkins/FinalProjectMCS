package com.laacompany.travelplanner.Handle;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.ModelClass.PlanMaster;
import com.laacompany.travelplanner.ModelClass.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyHandle {

    public static RequestQueue mRequestQueue;

    public static void init(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static void addNewUser(String user_id, String gender, String date_of_birth){
        String url = " https://us-central1-fir-crud-restapi-a8904.cloudfunctions.net/app/api/create";

        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", user_id);
            obj.put("date_of_birth", date_of_birth);
            obj.put("gender", gender);
            obj.put("planmasters", new ArrayList<String>());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("12345", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("12345", error.toString());
            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }

    public static void getCurrentUser(String user_id){
        String url="" + user_id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String date_of_birth = obj.getString("")
                            User user = new User();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(stringRequest);

    }

    public static void getAllDestinations(){
        String url = "https://us-central1-fir-crud-restapi-a8904.cloudfunctions.net/app/api/read/destinations";
        Handle.sDestinations.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject obj = (JSONObject) jsonArray.get(i);

                        String destinationId = obj.getString("id");
                        String name = obj.getString("name");
                        String address = obj.getString("address");
                        String country = obj.getString("country");
                        String previewUrl = obj.getString("preview_url");
                        String flagUrl = obj.getString("flag_url");
                        double rating = obj.getDouble("rating");
                        double latitude = obj.getDouble("latitude");
                        double longitude = obj.getDouble("longitude");
                        int totalVisitor = obj.getInt("total_visitor");
                        int visitorEachDay = obj.getInt("visitor_each_day");
                        int bestTimeStart = obj.getInt("best_time_start");
                        int bestTimeEnd = obj.getInt("best_time_end");
                        int openTime = obj.getInt("open_time");
                        int closeTime = obj.getInt("close_time");

                        Destination destination = new Destination(destinationId,name,address,country,previewUrl,flagUrl,rating,latitude,longitude,totalVisitor,visitorEachDay,bestTimeStart,bestTimeEnd,openTime,closeTime);
                        Handle.sDestinations.add(destination);
                    }
                } catch (JSONException e) {
                    Log.d("12345", e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("12345", error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }

    public static void getAllPlanMaster(){
//        return null;
    }

    public static void updateUser(){

    }

    public static void addPlanMaster(){

    }

    public static void updatePlanMaster(){

    }


    public static void customUpdate() {
        String url = "https://us-central1-fir-crud-restapi-a8904.cloudfunctions.net/app/api/update/3jyygD9M0q6ibV8XNiJ2";

        JSONObject obj = new JSONObject();
        try {
            obj.put("name","laa");
            obj.put("description","luis lgi bajak");
            obj.put("price", 123);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("12345", "udah update");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("12345", "eror mas" + error.toString());
            }
        });


        mRequestQueue.add(jsonObjectRequest);
    }
}
