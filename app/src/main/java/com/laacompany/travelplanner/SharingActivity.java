package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.Handle.VolleyHandle;

import java.util.List;

public class SharingActivity extends AppCompatActivity implements VolleyHandle.VolleyResponseListener {

    private TextView mTVResponse;
    private Button mBTNLaunch;
    private String id;
    private boolean hasShown = false;

    void initView(){
        mTVResponse = findViewById(R.id.id_activity_sharing_tv_response);
        mBTNLaunch = findViewById(R.id.id_activity_sharing_btn_launch);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        Handle.init(this);
        initView();


        Uri uri = getIntent().getData();
        if (uri == null) return;

        if (Handle.mAuth.getCurrentUser() == null){
            mTVResponse.setText("User not login");
            return;
        }

        VolleyHandle.listener = this;

        List<String> params = uri.getPathSegments();
        id = params.get(params.size()-1);

        VolleyHandle.getCurrentUser(Handle.mAuth.getCurrentUser().getUid());

        mBTNLaunch.setEnabled(false);
    }

    public void clickLaunch(View view) {
        startActivity(SplashActivity.newIntent(this));
        finish();
    }

    @Override
    public void onResponse(String functionResp) {

        if(functionResp.equals("getCurrentUser")){
            for(String pmId : VolleyHandle.sPlanMasterIds){
                Log.d("12345", pmId +" " +id);
                if (pmId.equals(id)){
                    mTVResponse.setText("User already has the PlanMaster");
                    mBTNLaunch.setEnabled(true);
                    return;
                }
            }
            VolleyHandle.sPlanMasterIds.add(id);
            VolleyHandle.updateUserPlanMaster();
        } else if (functionResp.equals("updateUserPlanMaster")){
            mTVResponse.setText("add Successful");
            mBTNLaunch.setEnabled(true);
        }

    }

    @Override
    public void onError(String functionResp) {
        if (!hasShown){
            Toast.makeText(this, R.string.internet_error, Toast.LENGTH_SHORT).show();
            hasShown = true;
        }
    }
}
