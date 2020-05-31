package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.laacompany.travelplanner.Storage.RegisterData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.laacompany.travelplanner.Storage.DataStorage.registerData;

public class RegisterActivity extends AppCompatActivity {

    public static TextInputEditText mEDTUsername, mEDTPassword, mEDTConfirmPassword, mEDTPhone, mEDTDof;
    public static TextInputLayout mLAYUsername, mLAYPassword, mLAYConfirmPassword, mLAYPhone, mLAYDof;
    public static TextView mTXTBack;
    public static Button mBTNRegister;
    public static RadioGroup mRGGroup;
    public static RadioButton mRBButton;
    public static CheckBox mCBCheckBox;
    public static DatePickerDialog dpdialog;
    public static SimpleDateFormat dateFormatter;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,RegisterActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();


        mTXTBack.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
            this.finish();
        });

        mEDTDof.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            dpdialog = new DatePickerDialog(RegisterActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);

                mEDTDof.setText(dateFormatter.format(newDate.getTime()));
                mLAYDof.setError(null);
            }, mYear, mMonth, mDay);

            dpdialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            dpdialog.show();

        });

        mBTNRegister.setOnClickListener(v -> {

            checkRegister();
        });

    }

    private void checkRegister() {
        boolean registerValid = true;

        String uname,pass="",pnum,birthdate,gender;


        int getRadioValue = mRGGroup.getCheckedRadioButtonId();
        mRBButton = findViewById(getRadioValue);



        //USERNAME CHECK
        if (!mEDTUsername.getText().toString().matches("^(?=.*\\d)(?=.*[a-zA-Z]).{3,25}$")){
            mLAYUsername.setError("Username must between 3 and 25 characters,1 digit and alphabetic");
            registerValid = false;
        }
        else mLAYUsername.setError(null);

        //PASSWORD CHECK

        if (!mEDTPassword.getText().toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$") ) {
            mLAYPassword.setError("Password must contain at least 1 lowercase letter, 1 uppercase letter,1 digit and more than 6 character");
            registerValid = false;
        }
        else {
            mLAYPassword.setError(null);
            pass = mEDTPassword.getText().toString();

            //CONFIRM PASSWORD CHECK

            if (!mEDTConfirmPassword.getText().toString().matches(pass)){
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

        if (registerValid){

            /**USERNAME EXISTS CHECK HERE
             your code here
             **/

            //ALL VALID GOES HERE
            uname = mEDTUsername.getText().toString();
            pnum = mEDTPhone.getText().toString();
            birthdate = Objects.requireNonNull(mEDTDof.getText()).toString();
            gender = mRBButton.getText().toString();

            //Toast Goes here and insert data
            registerData.add(new RegisterData(uname,pass,pnum,gender,birthdate));
            Toast.makeText(getApplicationContext(),"Register Successful",Toast.LENGTH_SHORT).show();
            RegisterActivity.this.finish();
        }
    }

    void init(){
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

        mEDTDof.setInputType(InputType.TYPE_NULL);
    }

    boolean isEmpty(TextInputEditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }
}
