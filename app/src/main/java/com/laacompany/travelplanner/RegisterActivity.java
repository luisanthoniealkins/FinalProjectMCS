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

import com.laacompany.travelplanner.Storage.DataStorage;
import com.laacompany.travelplanner.Storage.RegisterData;

import java.util.Calendar;

import static com.laacompany.travelplanner.Storage.DataStorage.registerData;

public class RegisterActivity extends AppCompatActivity {

    public static EditText etUname,etPass,etConfPass,etPnum,birthDate;
    public static Button btnBack,btnRegister;
    public static RadioGroup rdGroup;
    public static RadioButton rdButton;
    public static CheckBox checkBox;
    public static DatePickerDialog dpdialog;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,RegisterActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                dpdialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                dpdialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname,pass,cfpass,pnum,birthdate,gender;
                //Name 3 - 25 lenght containt at least 1 alphabetic and digit
                String regUname = "(?=.*[0-9])(?=.*[a-zA-Z]).{3,25}";
                //Contain 1 lowercase , 1 upper case , 1 digit
                String regPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}";
                uname = etUname.getText().toString();
                pass = etPass.getText().toString();
                cfpass = etConfPass.getText().toString();
                pnum = etPnum.getText().toString();
                birthdate = birthDate.getText().toString();
                int getRadioValue = rdGroup.getCheckedRadioButtonId();
                rdButton = findViewById(getRadioValue);

                if(uname.trim().equals("") || pass.trim().equals("") || cfpass.trim().equals("") || pnum.trim().equals("")
                        || birthdate.trim().equals("")){
                    Toast.makeText(RegisterActivity.this,"Please fill all coloumn",Toast.LENGTH_SHORT).show();
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
                if(rdGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegisterActivity.this,"Please choose your gender",Toast.LENGTH_SHORT).show();
                    return;
                }
                //checkbox
                if(!checkBox.isChecked()){
                    Toast.makeText(RegisterActivity.this,"Please fill the checkbox",Toast.LENGTH_SHORT).show();
                    return;
                }
                gender = rdButton.getText().toString();


                //Toast Go here and insert data
                registerData.add(new RegisterData(uname,pass,pnum,gender,birthdate));
            }
        });

    }

    void init(){
        etUname = findViewById(R.id.etUname);
        etPass = findViewById(R.id.etPass);
        etConfPass = findViewById(R.id.etConfPass);
        etPnum = findViewById(R.id.etPnum);
        birthDate = findViewById(R.id.birthDate);
        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);
        rdGroup = findViewById(R.id.rdGroup);
        checkBox = findViewById(R.id.checkBox);
    }
}
