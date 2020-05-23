package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PlanAdapter planAdapter;
    ArrayList<Plan> plans = new ArrayList<>();

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,PlanActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);


        plans.add(new Plan("Majapahit", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "08:54", 90));
//        plans.add(new Plan("Majapahit2", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "08:54", 90));
//        plans.add(new Plan("Majapahit3", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "08:54", 90));
//        plans.add(new Plan("Majapahit4", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "08:54", 90));

        mRecyclerView = findViewById(R.id.id_main_rv_plan_list);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        planAdapter = new PlanAdapter(this, plans);
        mRecyclerView.setAdapter(planAdapter);

    }


    public void clickAdd(View view) {
        plans.add(new Plan("Majapahit", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "08:54", 90));
        Toast.makeText(this,"oi",Toast.LENGTH_SHORT).show();
        planAdapter.setPlans(plans);
        planAdapter.notifyDataSetChanged();
    }

    public void clickDate(View view) {

    }

    public void clickTime(View view) {

    }
}
