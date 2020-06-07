package com.laacompany.travelplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.Handle.VolleyHandle;

public class LoginActivity extends AppCompatActivity implements VolleyHandle.VolleyResponseListener {

    private static final int REQ_SEND_SMS =0 ;

    private TextInputEditText mEDTUsername, mEDTPassword;
    private TextInputLayout mLAYUsername, mLAYPassword;
    private Button mBTNLogin;
    private TextView mTXTSignUp;
    private ProgressBar mPBLoading;
    private boolean isUserLoad, isPlanMastersLoad, hasShown;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,LoginActivity.class);
    }

    void initView(){
        mLAYUsername =findViewById(R.id.id_activity_login_iptlayout_username);
        mLAYPassword =findViewById(R.id.id_activity_login_iptlayout_password);
        mEDTUsername = findViewById(R.id.id_activity_login_edt_username);
        mEDTPassword = findViewById(R.id.id_activity_login_edt_password);
        mBTNLogin = findViewById(R.id.id_activity_login_btn_login);
        mTXTSignUp = findViewById(R.id.id_activity_login_txtView_signUpTxt);
        mPBLoading = findViewById(R.id.id_activity_login_PB_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


        mTXTSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });
        mBTNLogin.setOnClickListener(v -> {
            hasShown = false;
            validateLogin();
        });

        isUserLoad = isPlanMastersLoad = false;
        VolleyHandle.listener = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Handle.mAuth.getCurrentUser() != null){
            finish();
        }
    }

    private void validateLogin() {

        String email = mEDTUsername.getText().toString().trim();
        String password = mEDTPassword.getText().toString().trim();

        boolean login = true;

        if (isEmpty(mEDTUsername)){
            mLAYUsername.setError("Username must be filled");
            login = false;
        } else {
            mLAYUsername.setError(null);
        }

        if (isEmpty(mEDTPassword)){
            mLAYPassword.setError("Password must be filled");
            login = false;
        } else {
            mLAYPassword.setError(null);
        }

        mPBLoading.setVisibility(View.VISIBLE);
        if (!login) return;
        Handle.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    VolleyHandle.getCurrentUser(Handle.mAuth.getCurrentUser().getUid());
                    sendSMS();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    mPBLoading.setVisibility(View.GONE);
                }
            }
        });

    }

    private void sendSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            message();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},REQ_SEND_SMS);
        }
    }

    private void message() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("089699170496",null,"You are registered to AturinAja",null,null);
        Toast.makeText(this, "SMS sent. Register Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_SEND_SMS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                message();
            } else {
                Toast.makeText(getApplicationContext(),
                        "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean isEmpty(TextInputEditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }

    @Override
    public void onBackPressed() {
        Handle.isExit = true;
        super.onBackPressed();
    }

    @Override
    public void onResponse(String functionResp) {
        if (functionResp.equals("getCurrentUser")){
            isUserLoad = true;
        } else if (functionResp.equals("getAllPlanMasters")){
            isPlanMastersLoad = true;
        }
        if (isUserLoad && isPlanMastersLoad) {
            finish();
        }
    }

    @Override
    public void onError(String functionResp) {
        if (!hasShown){
            if (functionResp.equals("getCurrentUserNotFound")){
                if (Handle.mAuth.getCurrentUser() != null){
                    Handle.mAuth.getCurrentUser().delete();
                }
                Toast.makeText(this, "User not found, Please register", Toast.LENGTH_SHORT).show();
                mPBLoading.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, R.string.internet_error, Toast.LENGTH_SHORT).show();
            }
            hasShown = true;
        }

    }




}



//



