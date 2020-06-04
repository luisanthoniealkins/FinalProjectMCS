package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.laacompany.travelplanner.Fragment.CalendarFragment;
import com.laacompany.travelplanner.Fragment.ExploreFragment;
import com.laacompany.travelplanner.Fragment.HomeFragment;
import com.laacompany.travelplanner.Fragment.SettingFragment;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.Handle.VolleyHandle;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.ModelClass.PlanMaster;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SpaceNavigationView mSpaceNavigationView;
    private boolean mayClick=true;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, MainActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void initView(){
        mSpaceNavigationView = findViewById(R.id.id_main_nav_bar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Handle.isExit) finish();
        else if(Handle.mAuth.getCurrentUser() == null){
            startActivity(LoginActivity.newIntent(this));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //NAVIGATION BAR
        mSpaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        mSpaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_home_black_24dp));
        mSpaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_map_black_24dp));
        mSpaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_perm_contact_calendar_black_24dp));
        mSpaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_settings_black_24dp));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        mSpaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                if(!mayClick) return;
                mayClick=false;
                startActivity(PlanActivity.newIntent(MainActivity.this));
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ExploreFragment()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CalendarFragment()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingFragment()).commit();
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        //NAVIGATION BAR


    }

    @Override
    protected void onResume() {
        super.onResume();
        mayClick=true;
    }
}
