package com.laacompany.travelplanner;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VollyTest extends AppCompatActivity {

    private TextView mTVName, mTVContent, mTVCurhatList;

    private static final String URL_POST = "https://coba-api.douglasnugroho.com/doCurhat.php";
    private static final String URL_GET = "https://coba-api.douglasnugroho.com/getCurhat.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickSend(View view) {
        final String name = mTVName.getText().toString();
        final String curhatan = mTVContent.getText().toString();

        if (name.isEmpty() || curhatan.isEmpty()) {
            Toast.makeText(this, "Jangan kosong", Toast.LENGTH_SHORT).show();
            return;
        }

//            final JSONObject jsonBody = new JSONObject();
//
//            jsonBody.put("name", name);
//            jsonBody.put("curhat", curhatan);
//
//            final String mRequestBody = jsonBody.toString();


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("LOG_RESPONSE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_RESPONSE", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("name",name);
                headers.put("curhat",curhatan);
                return headers;
            }

        };

        queue.add(stringRequest);


    }

    public void clickReload(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mTVCurhatList.setText("");
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("halo", jsonArray.length()+"");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("halo",jsonObject.getString("id"));
                        mTVCurhatList.append(jsonObject.getString("id")+"\n");
                        mTVCurhatList.append(jsonObject.getString("name")+"\n");
                        mTVCurhatList.append(jsonObject.getString("curhat")+"\n");
                        Log.d("halo","idx = " + i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","Something when wrong");
            }
        });

        queue.add(stringRequest);
    }
}
