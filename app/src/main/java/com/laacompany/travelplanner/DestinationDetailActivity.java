package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DestinationDetailActivity extends AppCompatActivity {

    private static final String EXTRA_POS = "position_extra";

    public static Intent newIntent(Context packageContext, int position){
        Intent intent = new Intent(packageContext, DestinationDetailActivity.class);
        intent.putExtra(EXTRA_POS, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);
    }


}
