package com.laacompany.travelplanner.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.InterfaceAndCallback.ItemTouchHelperAdapter;
import com.laacompany.travelplanner.InterfaceAndCallback.ItemTouchHelperViewHolder;
import com.laacompany.travelplanner.InterfaceAndCallback.OnStartDragListener;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.R;

import java.util.ArrayList;
import java.util.Collections;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private ArrayList<Plan> mPlans;
    private final OnStartDragListener mDragStartListener;

    public PlanAdapter(Context context, ArrayList<Plan> plans, OnStartDragListener dragStartListener){
        mContext = context;
        mPlans = plans;
        mDragStartListener = dragStartListener;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(LayoutInflater.from(mContext),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanViewHolder holder, int position) {
        holder.bind(mPlans.get(position),position);

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

        for(int i = 1; i < Handle.sPLans.size(); i++){
            int departTime = Handle.sPLans.get(i-1).getArrivedTime()+Handle.sPLans.get(i-1).getDuration();
            int travelTime = 0;
            Handle.sPLans.get(i).setArrivedTime(departTime+travelTime);
        }

        setPlans(Handle.sPLans);
        notifyDataSetChanged();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private TextView mTVTitle, mTVName, mTVAddress, mTVSummary;
        private Button mBTNArrivedTime, mBTNDuration;
        private ImageView mIVPreview, mIVHandle;
        private PlanViewHolder holder;

        public PlanViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_plan, parent, false));

            mTVTitle = itemView.findViewById(R.id.id_item_plan_tv_title);
            mTVName = itemView.findViewById(R.id.id_item_plan_tv_name);
            mTVAddress = itemView.findViewById(R.id.id_item_plan_tv_address);
            mTVSummary = itemView.findViewById(R.id.id_item_plan_tv_summary);
            mBTNArrivedTime = itemView.findViewById(R.id.id_item_plan_btn_arrived_time);
            mBTNDuration = itemView.findViewById(R.id.id_item_plan_btn_duration);
            mIVPreview = itemView.findViewById(R.id.id_item_plan_iv_preview);
            mIVHandle = itemView.findViewById(R.id.id_item_plan_iv_handle);

            holder = this;
        }

        public void bind(Plan plan, int position){

            String title = "Plan " + (position+1);
            String duration = plan.getDuration()+" minutes";
            String summary = Handle.getHourFormat(plan.getArrivedTime()) + " - " + Handle.getHourFormat(plan.getArrivedTime()+plan.getDuration());

            Glide.with(mContext)
                    .load(plan.getImage_URL())
                    .into(mIVPreview);

            mTVTitle.setText(title);
            mTVName.setText(plan.getName());
            mTVAddress.setText(plan.getAddress());
            mTVSummary.setText(summary);
            mBTNArrivedTime.setText(Handle.getHourFormat(plan.getArrivedTime()));
            mBTNDuration.setText(duration);

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

        @Override
        public void onItemClear() {
            refreshData();
//            Toast.makeText(mContext, Handle.sPLans.size()+"", Toast.LENGTH_SHORT).show();
            itemView.setBackgroundColor(0);
        }
    }
}
