package com.laacompany.travelplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.laacompany.travelplanner.Adapter.PlanAdapter;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.InterfaceAndCallback.OnStartDragListener;
import com.laacompany.travelplanner.InterfaceAndCallback.SimpleItemTouchHelperCallback;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.ModelClass.PlanMaster;
import com.laacompany.travelplanner.PickerDialog.DialogDuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlanActivity extends AppCompatActivity  implements OnStartDragListener, DialogDuration.DurationDialogListener {

    private static final String EXTRA_MODE = "mode_extra";
    private static final String EXTRA_PLAN_ID = "plan_id_extra";
    private static final String EXTRA_DATE = "date_extra";
    private static final String EXTRA_DESTINATION_ID = "destination_id_extra";
    public static final int REQUEST_CODE_SEARCH = 1;
    public static final String EXTRA_RESULT_DESTINATION_ID = "result_destination_id_extra";

    private EditText mEDTTitle;
    private Button mBTNDate, mBTNStartTime;
    private TextView mTVEndTime;

    private RecyclerView mRecyclerView;
    private PlanAdapter planAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private PlanMaster mPlanMaster;

    private int mode;

//    int debug = 0;

    public static Intent newIntentDestination(Context packageContext, String destinationId){
        Intent intent = new Intent(packageContext,PlanActivity.class);
        intent.putExtra(EXTRA_MODE, 0);
        intent.putExtra(EXTRA_DESTINATION_ID, destinationId);
        return intent;
    }

    public static Intent newIntentCalendar(Context packageContext, long date){
        Intent intent = new Intent(packageContext,PlanActivity.class);
        intent.putExtra(EXTRA_MODE,0);
        intent.putExtra(EXTRA_DATE, date);
        return intent;
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext,PlanActivity.class);
        intent.putExtra(EXTRA_MODE, 0);
        return intent;
    }

    public static Intent newIntentView(Context packageContext, String planId){
        Intent intent = new Intent(packageContext,PlanActivity.class);
        intent.putExtra(EXTRA_MODE, 1);
        intent.putExtra(EXTRA_PLAN_ID, planId);
        return intent;
    }

    private void initView(){
        mEDTTitle = findViewById(R.id.id_activity_plan_edt_title);
        mBTNDate = findViewById(R.id.id_activity_plan_btn_date);
        mBTNStartTime = findViewById(R.id.id_activity_plan_btn_start_time);
        mTVEndTime = findViewById(R.id.id_activity_plan_tv_end_time);
        mRecyclerView = findViewById(R.id.id_activity_plan_rv_plan_list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        initView();


        // MODE
        // 0 = NEW
        // 1 = UPDATE
        mode = getIntent().getIntExtra(EXTRA_MODE,-1);
        if(mode == 0){
            mPlanMaster = new PlanMaster(Handle.generatePlanID());
            String destinationID;
            if(getIntent().hasExtra(EXTRA_DATE)){
                long time = getIntent().getLongExtra(EXTRA_DATE,0);
                Date date = new Date();
                date.setTime(time);
                String dateString = new SimpleDateFormat("EEEE, dd MMM YYYY").format(date);
                mPlanMaster.setEventDate(date);
                mBTNDate.setText(dateString);
            }

            String startTime = Handle.getHourFormat(0);
            mPlanMaster.setTimeStart(0);
            mBTNStartTime.setText(startTime);

            mPlanMaster.setPlans(new ArrayList<Plan>());

            if(getIntent().hasExtra(EXTRA_DESTINATION_ID)){
                destinationID = getIntent().getStringExtra(EXTRA_DESTINATION_ID);
                Destination dest = Handle.getDestination(destinationID);
                mPlanMaster.getPlans().add(new Plan( dest.getDestinationId(),dest.getName(), dest.getAddress(), dest.getPreviewUrl(),0, 30));
            }

        } else if (mode == 1){
            String plan_id = getIntent().getStringExtra(EXTRA_PLAN_ID);
            for(PlanMaster planMaster : Handle.sPlanMasters){
                if(planMaster.getMasterPlanId().equals(plan_id)){
                    mPlanMaster = new PlanMaster();
                    mPlanMaster.setPlanMaster(planMaster);
                    ArrayList<Plan> plans = new ArrayList<>(mPlanMaster.getPlans());
                    mPlanMaster.setPlans(plans);
                    break;
                }
            }

//            for(int i = 0; i < Handle.sPlanMasters.size(); i++){
//                if(Handle.sPlanMasters.get(i).getPlan_id().equals(plan_id)){
//                    debug = i;
//                    Log.d("123",debug+"");
//                    break;
//                }
//            }


            String date = new SimpleDateFormat("EEEE, dd MMM YYYY").format(mPlanMaster.getEventDate());
            String startTime = Handle.getHourFormat(mPlanMaster.getTimeStart());

            mEDTTitle.setText(mPlanMaster.getEventTitle());
            mBTNDate.setText(date);
            mBTNStartTime.setText(startTime);

        }



        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        planAdapter = new PlanAdapter(this, mPlanMaster.getPlans(), this, mBTNStartTime, mTVEndTime, mPlanMaster.getLatitude(), mPlanMaster.getLongitude());
        mRecyclerView.setAdapter(planAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(planAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        planAdapter.refreshData();
    }


    public void clickAdd(View view) {
        mPlanMaster.getPlans().add(new Plan("DES_1" ,"Majapahit", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 1020, 90));
        mPlanMaster.getPlans().add(new Plan("DES_2" ,"Majapahit2", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 1020, 90));
        mPlanMaster.getPlans().add(new Plan("DES_3" ,"Majapahit3", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 1020, 90));

        planAdapter.setPlans(mPlanMaster.getPlans());
        planAdapter.refreshData();
    }

    public void clickDate(View view) {
        Calendar currentDate = Calendar.getInstance();;

        if(mPlanMaster.getEventDate() != null){
            currentDate.setTime(mPlanMaster.getEventDate());
        }

        int DD = currentDate.get(Calendar.DAY_OF_MONTH);
        int MM = currentDate.get(Calendar.MONTH);
        int YYYY = currentDate.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PlanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int YYYY, int MM, int DD) {
                        GregorianCalendar gc = new GregorianCalendar(YYYY,MM,DD);
                        Date date = new Date(gc.getTimeInMillis());
                        mPlanMaster.setEventDate(date);
                        String dateString = new SimpleDateFormat("EEEE, dd MMM YYYY").format(mPlanMaster.getEventDate());
                        mBTNDate.setText(dateString);
                    }
                }, YYYY, MM, DD);
        datePickerDialog.show();
    }

    public void clickTime(View view) {

        int startTime = mPlanMaster.getTimeStart();
        int startHour = startTime/60;
        int startMinute = startTime%60;

        TimePickerDialog timePickerDialog = new TimePickerDialog(PlanActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int minutes = hourOfDay*60+minute;
                        mPlanMaster.setTimeStart(minutes);
                        mBTNStartTime.setText(Handle.getHourFormat(minutes));
                        planAdapter.refreshData();
                    }
        },startHour, startMinute, true);
        timePickerDialog.show();

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    public void clickSave(View view) {

        boolean valid = validation();

        if(valid) {
            if (mode == 0){
                Handle.sPlanMasters.add(mPlanMaster);
            } else {
                for(int i = 0; i < Handle.sPlanMasters.size(); i++){
                    if(Handle.sPlanMasters.get(i).getMasterPlanId().equals(mPlanMaster.getMasterPlanId())){
                        Handle.sPlanMasters.get(i).setPlanMaster(mPlanMaster);
                        break;
                    }
                }
            }
            finish();
        }

    }

    private boolean validation(){

        String title = mEDTTitle.getText().toString();
        String date = mBTNDate.getText().toString();

        boolean valid = true;

        if (title.isEmpty()){
            Toast.makeText(this, "Please input event name", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mPlanMaster.setEventTitle(title);
        }

        if (date.equals("Select Date")){
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    @Override
    public void applyTime(int minutes) {
        mPlanMaster.getPlans().get(PlanAdapter.selectedPos).setDuration(minutes);
        planAdapter.refreshData();
//        Toast.makeText(this, Handle.getHourFormat(minutes), Toast.LENGTH_SHORT).show();
    }

    public void clickSimulate(View view) {
        if(mPlanMaster.getPlans().size() > 0){
            Handle.sCurrentRoutes.clear();
            for(Plan plan : mPlanMaster.getPlans()){
                for(Destination destination : Handle.sDestinations){
                    if(plan.getDestinationId().equals(destination.getDestinationId())){
                        Handle.sCurrentRoutes.add(Pair.create(destination.getLongitude(),destination.getLatitude()));
                        break;
                    }
                }
            }
            startActivity(MapsActivity.newIntent(this));
        } else {
            Toast.makeText(this, "Require at least one destinations", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SEARCH){
            if(resultCode == Activity.RESULT_OK){
                String destinationId = data.getStringExtra(EXTRA_RESULT_DESTINATION_ID);
                Destination destination = Handle.getDestination(destinationId);

                mPlanMaster.getPlans().get(PlanAdapter.selectedPos).setDestinationId(destinationId);
                mPlanMaster.getPlans().get(PlanAdapter.selectedPos).setName(destination.getName());
                mPlanMaster.getPlans().get(PlanAdapter.selectedPos).setAddress(destination.getAddress());
                mPlanMaster.getPlans().get(PlanAdapter.selectedPos).setPreviewUrl(destination.getPreviewUrl());
                planAdapter.notifyDataSetChanged();

            } else if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }

    public void clickDeleteMasterPlan(View view) {
    }
}
