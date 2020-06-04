package com.laacompany.travelplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.Handle.VolleyHandle;
import com.laacompany.travelplanner.ModelClass.User;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements VolleyHandle.VolleyResponseListener {

    public TextInputEditText mEDTUsername, mEDTPassword, mEDTConfirmPassword, mEDTPhone, mEDTDof;
    public TextInputLayout mLAYUsername, mLAYPassword, mLAYConfirmPassword, mLAYPhone, mLAYDof;
    public TextView mTXTBack;
    public Button mBTNRegister;
    public RadioGroup mRGGroup;
    public RadioButton mRBButton;
    public CheckBox mCBCheckBox;
    public DatePickerDialog dpdialog;
    public SimpleDateFormat dateFormatter;
    private ProgressBar mPBLoading;

    public Date pickedDate = new Date();
    private boolean hasShown;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,RegisterActivity.class);
    }

    void initView(){
        mLAYUsername = findViewById(R.id.id_activity_register_iptlayout_username);
        mLAYPassword = findViewById(R.id.id_activity_register_iptlayout_password);
        mLAYConfirmPassword = findViewById(R.id.id_activity_register_iptlayout_confirm_password);
        mLAYDof = findViewById(R.id.id_activity_register_iptlayout_dof);
        mLAYPhone = findViewById(R.id.id_activity_register_iptlayout_phone);


        mEDTUsername = findViewById(R.id.id_activity_register_edt_username);
        mEDTPassword = findViewById(R.id.id_activity_register_edt_password);
        mEDTConfirmPassword = findViewById(R.id.id_activity_register_edt_confirmation_password);
        mEDTPhone = findViewById(R.id.id_activity_register_edt_phone);
        mEDTDof = findViewById(R.id.id_activity_register_edt_birthDate);
        mTXTBack = findViewById(R.id.id_activity_register_btn_back);
        mBTNRegister = findViewById(R.id.id_activity_register_btn_register);
        mRGGroup = findViewById(R.id.id_activity_register_rd_rdGroup);
        mCBCheckBox = findViewById(R.id.id_activity_register_cb_cbox);

        mPBLoading = findViewById(R.id.id_activity_register_pb_loading);

        mEDTDof.setInputType(InputType.TYPE_NULL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();


        mTXTBack.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
            this.finish();
        });

        mEDTDof.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            dateFormatter = new SimpleDateFormat("EEEE, dd MMM YYYY", Locale.US);

            dpdialog = new DatePickerDialog(RegisterActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);
                pickedDate = newDate.getTime();
                mEDTDof.setText(dateFormatter.format(newDate.getTime()));
                mLAYDof.setError(null);
            }, mYear, mMonth, mDay);

            dpdialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            dpdialog.show();

        });

        mBTNRegister.setOnClickListener(v -> {
            hasShown = false;
            checkRegister();
        });

        VolleyHandle.listener = this;
    }

    private void checkRegister() {
        boolean registerValid = true;

        String email = mEDTUsername.getText().toString();
        final String password = mEDTPassword.getText().toString();
        String gender = "Male";
        String phone = mEDTPhone.getText().toString();
//        String dob = mEDTDof.getText().toString();

        int getRadioValue = mRGGroup.getCheckedRadioButtonId();
        mRBButton = findViewById(getRadioValue);

        //USERNAME CHECK
//        if (!mEDTUsername.getText().toString().matches("^(?=.*\\d)(?=.*[a-zA-Z]).{3,25}$")){
//            mLAYUsername.setError("Username must between 3 and 25 characters,1 digit and alphabetic");
//            registerValid = false;
//        }
//        else mLAYUsername.setError(null);

        //PASSWORD CHECK

        if (!mEDTPassword.getText().toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$") ) {
            mLAYPassword.setError("Password must contain at least 1 lowercase letter, 1 uppercase letter,1 digit and more than 6 character");
            registerValid = false;
        }
        else {
            mLAYPassword.setError(null);
            //CONFIRM PASSWORD CHECK

            if (!mEDTConfirmPassword.getText().toString().matches(password)){
                mLAYConfirmPassword.setError("Must same with password");
                registerValid = false;
            }
            else {
                mLAYConfirmPassword.setError(null);
            }
        }

        //PHONE NUMBER CHECK
        boolean flag = true;
        int len = mEDTPhone.getText().toString().length();
        if (len>6){
            for (int i =0 ; i<len ; i++){
                if (!(mEDTPhone.getText().toString().charAt(i)>='0' &&
                        mEDTPhone.getText().toString().charAt(i)<='9'))
                    flag = false;
            }
            if (flag) mLAYPhone.setError(null);

            else{
                mLAYPhone.setError("Phone number must contains digit only");
                registerValid = false;
            }

        }
        else {
            mLAYPhone.setError("Phone number must between 10 and 12 character");
            registerValid = false;
        }

        //DOF CHECK
        if (isEmpty(mEDTDof)){
            mLAYDof.setError("This input must be filled");
            registerValid = false;
        }

        //RADIO BUTTON CHECK

        if(mRGGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(RegisterActivity.this,"Please choose your gender",Toast.LENGTH_SHORT).show();
            registerValid = false;
        }

        //CHECKBOX CHECK

        if(!mCBCheckBox.isChecked()){
            Toast.makeText(RegisterActivity.this,"Please fill the checkbox",Toast.LENGTH_SHORT).show();
            registerValid = false;
        }

        if (!registerValid) return;;

        mPBLoading.setVisibility(View.VISIBLE);
        Handle.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful()) {
                    final String userId;
                    userId = Handle.mAuth.getCurrentUser().getUid();
//                    Handle.mAuth.getCurrentUser().updatePhoneNumber(phone);

                    VolleyHandle.addNewUser(userId, gender, Handle.convDateToLong(pickedDate), phone);
                    Handle.sCurrentUser = new User(userId, email,phone,gender,pickedDate);
                } else {
                    //Toast.makeText(SignupActivity.this, "User Failed to Register", Toast.LENGTH_SHORT).show();
                    mPBLoading.setVisibility(View.GONE);
                    if ( task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    boolean isEmpty(TextInputEditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }

    @Override
    public void onResponse(String functionResp) {
        Log.d("12345","woii" + functionResp);
        if (functionResp.equals("addNewUser")){
            Toast.makeText(this, "User Register Success", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onError(String functionResp) {
        if (!hasShown){
            mPBLoading.setVisibility(View.GONE);
            Toast.makeText(this, R.string.internet_error, Toast.LENGTH_SHORT).show();
            if (Handle.mAuth.getCurrentUser() != null){
                Handle.mAuth.getCurrentUser().delete();
            }
            hasShown = true;

        }
    }
}
