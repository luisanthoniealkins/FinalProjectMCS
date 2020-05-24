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
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

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
        Handle.sDestinations.add(new Destination("Majapahit", "A", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("Majapahit", "B", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("Majapahit", "V", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("Kuningan", "D", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        Handle.sDestinations.add(new Destination("Kuningan2", "C", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));

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
