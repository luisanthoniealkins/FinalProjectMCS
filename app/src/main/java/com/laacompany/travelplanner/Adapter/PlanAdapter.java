package com.laacompany.travelplanner.Adapter;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.InterfaceAndCallback.ItemTouchHelperAdapter;
import com.laacompany.travelplanner.InterfaceAndCallback.ItemTouchHelperViewHolder;
import com.laacompany.travelplanner.InterfaceAndCallback.OnStartDragListener;
import com.laacompany.travelplanner.MainActivity;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.PickerDialog.DialogDuration;
import com.laacompany.travelplanner.PlanActivity;
import com.laacompany.travelplanner.R;

import java.util.ArrayList;
import java.util.Collections;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private static ArrayList<Plan> mPlans;
    public static int selectedPos;
    private final OnStartDragListener mDragStartListener;
    private Button mBTNStartTime;
    private TextView mTVEndTime;


    public PlanAdapter(Context context, ArrayList<Plan> plans, OnStartDragListener dragStartListener, Button startTime, TextView endTime){
        mContext = context;
        mPlans = plans;
        mDragStartListener = dragStartListener;
        mBTNStartTime = startTime;
        mTVEndTime = endTime;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(LayoutInflater.from(mContext),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        holder.bind(mPlans.get(position));

    }

    public void setPlans(ArrayList<Plan> plans){
        mPlans = plans;
    }

    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mPlans.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mPlans, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mPlans, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void refreshData(){

        if(mPlans.size() > 0){
            String startStrings[] = mBTNStartTime.getText().toString().split(":");
            int minutes = Integer.parseInt(startStrings[0])*60 + Integer.parseInt(startStrings[1]);
            mPlans.get(0).setArrivedTime(minutes);
        }

        for(int i = 1; i < mPlans.size(); i++){
            int departTime = mPlans.get(i-1).getArrivedTime()+mPlans.get(i-1).getDuration();
            int travelTime = 0;
            mPlans.get(i).setArrivedTime(departTime+travelTime);
        }

        if(mPlans.size() > 0){
            int minutes = mPlans.get(mPlans.size()-1).getArrivedTime() + mPlans.get(mPlans.size()-1).getDuration();
            mTVEndTime.setText(Handle.getHourFormat(minutes));
        }

        setPlans(mPlans);
        notifyDataSetChanged();
    }

    public void refreshAt(int minutes, int position){
        mPlans.get(position).setDuration(minutes);

        for(int i = position+1; i < mPlans.size(); i++){
            int departTime = mPlans.get(i-1).getArrivedTime()+mPlans.get(i-1).getDuration();
            int travelTime = 0;
            mPlans.get(i).setArrivedTime(departTime+travelTime);
        }
        setPlans(mPlans);
        notifyDataSetChanged();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private TextView mTVTitle, mTVName, mTVAddress, mTVTravelTime,mTVArrivedTime, mTVSummary;
        private Button mBTNDuration, mBTNDelete;
        private ImageView mIVPreview, mIVHandle;
        private PlanViewHolder holder;

        public PlanViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_plan, parent, false));

            mTVTitle = itemView.findViewById(R.id.id_item_plan_tv_title);
            mTVName = itemView.findViewById(R.id.id_item_plan_tv_name);
            mTVAddress = itemView.findViewById(R.id.id_item_plan_tv_address);
            mTVSummary = itemView.findViewById(R.id.id_item_plan_tv_summary);
            mTVTravelTime = itemView.findViewById(R.id.id_item_plan_tv_travel_time);
            mTVArrivedTime = itemView.findViewById(R.id.id_item_plan_tv_arrived_time);
            mBTNDuration = itemView.findViewById(R.id.id_item_plan_btn_duration);
            mBTNDelete = itemView.findViewById(R.id.id_item_plan_btn_delete);
            mIVPreview = itemView.findViewById(R.id.id_item_plan_iv_preview);
            mIVHandle = itemView.findViewById(R.id.id_item_plan_iv_handle);

            holder = this;
        }

        public void bind(Plan plan){

            String title = "Plan " + (getAdapterPosition()+1);
            String duration = plan.getDuration()+" minutes";
            String summary = Handle.getHourFormat(plan.getArrivedTime()) + " - " + Handle.getHourFormat(plan.getArrivedTime()+plan.getDuration());

            Glide.with(mContext)
                    .load(plan.getImage_URL())
                    .into(mIVPreview);

            mTVTitle.setText(title);
            mTVName.setText(plan.getName());
            mTVAddress.setText(plan.getAddress());
            mTVSummary.setText(summary);
            mBTNStartTime.setText(Handle.getHourFormat(plan.getArrivedTime()));
            mBTNDuration.setText(duration);


            mBTNDuration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogDuration dialogDuration = new DialogDuration();
                    dialogDuration.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "duration dialog");
                    selectedPos = getAdapterPosition();
                }
            });

            mBTNDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlanAdapter.mPlans.remove(getAdapterPosition());
                    refreshData();
                }
            });

            mIVHandle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onItemClear() {
            refreshData();
            itemView.setBackgroundColor(R.color.colorDefault);
        }
    }
}
