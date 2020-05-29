package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.laacompany.travelplanner.Storage.RegisterData;

import java.util.Calendar;

import static com.laacompany.travelplanner.Storage.DataStorage.registerData;

public class RegisterActivity extends AppCompatActivity {

    public static EditText mEDTUsername, mEDTPassword, mEDTConfirmPassword, mEDTPhone, mEDTbirthDate;
    public static Button mBTNBack, mBTNRegister;
    public static RadioGroup mRGGroup;
    public static RadioButton mRBButton;
    public static CheckBox mCBCheckBox;
    public static DatePickerDialog dpdialog;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,RegisterActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        mEDTbirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                dpdialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEDTbirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                dpdialog.show();
            }
        });

        mBTNRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname,pass,cfpass,pnum,birthdate,gender;
                //Name 3 - 25 lenght containt at least 1 alphabetic and digit
                String regUname = "(?=.*[0-9])(?=.*[a-zA-Z]).{3,25}";
                //Contain 1 lowercase , 1 upper case , 1 digit
                String regPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}";
                uname = mEDTUsername.getText().toString();
                pass = mEDTPassword.getText().toString();
                cfpass = mEDTConfirmPassword.getText().toString();
                pnum = mEDTPhone.getText().toString();
                birthdate = mEDTbirthDate.getText().toString();
                int getRadioValue = mRGGroup.getCheckedRadioButtonId();
                mRBButton = findViewById(getRadioValue);

                if(uname.trim().equals("") || pass.trim().equals("") || cfpass.trim().equals("") || pnum.trim().equals("")
                        || birthdate.trim().equals("")){
                    Toast.makeText(RegisterActivity.this,"Please fill all column",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(uname.length() < 3){
                    Toast.makeText(RegisterActivity.this,"Username too short",Toast.LENGTH_SHORT).show();
                    return;
                }else if(uname.length() > 25){
                    Toast.makeText(RegisterActivity.this,"Username too long",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(uname.matches(regUname) == false){
                    Toast.makeText(RegisterActivity.this,"Please contain 1 digit",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length() < 6){
                    Toast.makeText(RegisterActivity.this,"Password too short",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Confirmation regex password & compare password
                if(pass.matches(regPass) == false){
                    Toast.makeText(RegisterActivity.this,"Please contain 1 uppercase and 1 digit",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cfpass.compareTo(pass) != 0){
                    Toast.makeText(RegisterActivity.this,"Password not same",Toast.LENGTH_SHORT).show();
                    return;
                }

                //phonenumber check
                if(pnum.length() < 10){
                    Toast.makeText(RegisterActivity.this,"Phone number too short",Toast.LENGTH_SHORT).show();
                    return;
                }else if(pnum.length() > 12){
                    Toast.makeText(RegisterActivity.this,"Phone number too long",Toast.LENGTH_SHORT).show();
                    return;
                }

                //radiobutton
                if(mRGGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegisterActivity.this,"Please choose your gender",Toast.LENGTH_SHORT).show();
                    return;
                }
                //checkbox
                if(!mCBCheckBox.isChecked()){
                    Toast.makeText(RegisterActivity.this,"Please fill the checkbox",Toast.LENGTH_SHORT).show();
                    return;
                }
                gender = mRBButton.getText().toString();


                //Toast Go here and insert data
                registerData.add(new RegisterData(uname,pass,pnum,gender,birthdate));
            }
        });

    }

    void init(){
        mEDTUsername = findViewById(R.id.id_activity_register_edt_username);
        mEDTPassword = findViewById(R.id.id_activity_register_edt_password);
        mEDTConfirmPassword = findViewById(R.id.id_activity_register_edt_confirmation_password);
        mEDTPhone = findViewById(R.id.id_activity_register_edt_phone);
        mEDTbirthDate = findViewById(R.id.id_activity_register_edt_birthDate);
        mBTNBack = findViewById(R.id.id_activity_register_btn_back);
        mBTNRegister = findViewById(R.id.id_activity_register_btn_register);
        mRGGroup = findViewById(R.id.id_activity_register_rd_rdGroup);
        mCBCheckBox = findViewById(R.id.id_activity_register_cb_cbox);
    }
}
