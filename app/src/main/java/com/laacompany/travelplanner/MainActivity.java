package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.laacompany.travelplanner.Fragment.CalendarFragment;
import com.laacompany.travelplanner.Fragment.ExploreFragment;
import com.laacompany.travelplanner.Fragment.HomeFragment;
import com.laacompany.travelplanner.Fragment.SettingFragment;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.ModelClass.PlanMaster;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SpaceNavigationView mSpaceNavigationView;
    private boolean mayClick=true;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void initView(){
        mSpaceNavigationView = findViewById(R.id.id_main_nav_bar);


        //TEMPORARY
        Handle.sDestinations.add(new Destination("DES_1","Majapahit", "A", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 3.7F, 3.7F,1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("DES_2","Majapahit2","B", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 3.7F,3.7F,1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("DES_3","Majapahit3", "V", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F,3.7F,3.7F, 1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("DES_4","Kuningan", "D", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 3.7F,3.7F,1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("DES_5","Kuningan2", "C", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 3.7F,3.7F,1000, 20, 100, 300, 120, 240));

        Handle.sPlanMasters.add(new PlanMaster("PID_0","Holiday#1",new Date(), 200, -1.5555F, -120F));
        Handle.sPlanMasters.get(0).getPlans().add(new Plan("Majapahit", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 1020, 90));

        Calendar c = Calendar.getInstance(), c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
        c.set(2020,4,2);
        c1.set(2020,4,3);
        c2.set(2020,4,5);
        Handle.sPlanMasters.add(new PlanMaster("PID_1","Holiday#2",c.getTime(), 200, -1.5555F, -120F));
        Handle.sPlanMasters.add(new PlanMaster("PID_2","Holiday#3",c1.getTime(), 200, -1.5555F, -120F));
        Handle.sPlanMasters.add(new PlanMaster("PID_3","Holiday#4",c2.getTime(), 200, -1.5555F, -120F));
        Handle.sPlanMasters.add(new PlanMaster("PID_4","Holiday#5",c2.getTime(), 200, -1.5555F, -120F));
        Handle.sPlanMasters.add(new PlanMaster("PID_5","Holiday#6",c2.getTime(), 200, -1.5555F, -120F));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handle.init();
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
