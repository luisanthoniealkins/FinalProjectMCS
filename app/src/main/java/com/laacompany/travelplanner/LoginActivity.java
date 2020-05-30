package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    public static TextInputEditText mEDTUsername, mEDTPassword;
    public static TextInputLayout mLAYUsername, mLAYPassword;
    public static Button mBTNLogin;
    public static TextView mTXTSignUp;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,LoginActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        mTXTSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });
        mBTNLogin.setOnClickListener(v -> {
            validateLogin();
        });
    }

    private void validateLogin() {

        boolean login = true;

        if (isEmpty(mEDTUsername)){
            mLAYUsername.setError("Username must be filled");
            login = false;
        }
        else{
            mLAYUsername.setError(null);
        }

        if (isEmpty(mEDTPassword)){
            mLAYPassword.setError("Password must be filled");
            login = false;
        }
        else{
            mLAYPassword.setError(null);
        }

        if (login){
            //USER CHECK GOES HERE
        }
    }

    boolean isEmpty(TextInputEditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }


    void init(){
        mLAYUsername =findViewById(R.id.id_activity_login_iptlayout_username);
        mLAYPassword =findViewById(R.id.id_activity_login_iptlayout_password);
        mEDTUsername = findViewById(R.id.id_activity_login_edt_username);
        mEDTPassword = findViewById(R.id.id_activity_login_edt_password);
        mBTNLogin = findViewById(R.id.id_activity_login_btn_login);
        mTXTSignUp = findViewById(R.id.id_activity_login_txtView_signUpTxt);
    }
}
