package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.laacompany.travelplanner.Adapter.PlanAdapter;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.InterfaceAndCallback.OnStartDragListener;
import com.laacompany.travelplanner.InterfaceAndCallback.SimpleItemTouchHelperCallback;
import com.laacompany.travelplanner.ModelClass.Plan;

public class PlanActivity extends AppCompatActivity  implements OnStartDragListener{

    private RecyclerView mRecyclerView;
    private PlanAdapter planAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext,PlanActivity.class);
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.id_main_rv_plan_list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        initView();

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        planAdapter = new PlanAdapter(this, Handle.sPLans, this);
        mRecyclerView.setAdapter(planAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(planAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }


    public void clickAdd(View view) {
        Handle.sPLans.add(new Plan("Majapahit", "Jalan Menteng", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 1020, 90));
        planAdapter.setPlans(Handle.sPLans);
        planAdapter.notifyDataSetChanged();
//        Toast.makeText(this,"oi",Toast.LENGTH_SHORT).show();
    }

    public void clickDate(View view) {

    }

    public void clickTime(View view) {

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


}
