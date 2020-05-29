package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.laacompany.travelplanner.Adapter.ExploreAdapter;
import com.laacompany.travelplanner.Adapter.PlanAdapter;
import com.laacompany.travelplanner.Handle.Handle;

public class SearchActivity extends AppCompatActivity {

    private static final String EXTRA_LATITUDE = "latitude_extra";
    private static final String EXTRA_LONGITUDE = "longitude_extra";
    private RecyclerView mRecyclerView;

    public static Intent newIntent(Context packageContext, double latitude, double longitude){
        Intent intent = new Intent(packageContext, SearchActivity.class);
        intent.putExtra(EXTRA_LATITUDE,latitude);
        intent.putExtra(EXTRA_LONGITUDE,longitude);
        return intent;
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.id_activity_search_rv_destination_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExploreAdapter exploreAdapter = new ExploreAdapter(this, Handle.sDestinations, true);
        mRecyclerView.setAdapter(exploreAdapter);
    }



}
