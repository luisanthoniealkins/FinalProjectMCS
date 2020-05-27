package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.PickerDialog.DialogDuration;

public class DestinationDetailActivity extends AppCompatActivity implements DialogDuration.DurationDialogListener {

    private static final String EXTRA_POS = "position_extra";

    private TextView mTVName;

    private Destination mDestination;

    public static Intent newIntent(Context packageContext, int position){
        Intent intent = new Intent(packageContext, DestinationDetailActivity.class);
        intent.putExtra(EXTRA_POS, position);
        return intent;
    }

    private void initView(){
        mTVName = findViewById(R.id.id_activity_detaildes_tv_name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        initView();

        int index = getIntent().getIntExtra(EXTRA_POS,0);

        mDestination = Handle.getDestination(index);

        mTVName.setText(mDestination.getName());
    }


    public void clickPlan(View view) {
//        DialogDuration dialogDuration = new DialogDuration();
//        dialogDuration.show(getSupportFragmentManager(), "duration dialog");
        startActivity(PlanActivity.newIntentDestination(this, mDestination.getDestination_id()));
    }

    @Override
    public void applyTime(int minutes) {
        Toast.makeText(this,minutes+"",Toast.LENGTH_SHORT).show();
    }
}
