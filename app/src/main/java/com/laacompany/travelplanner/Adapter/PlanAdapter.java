package com.laacompany.travelplanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.travelplanner.Interface.ItemTouchHelperAdapter;
import com.laacompany.travelplanner.ModelClass.Plan;
import com.laacompany.travelplanner.R;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private ArrayList<Plan> mPlans;

    public PlanAdapter(Context context, ArrayList<Plan> plans){
        mContext = context;
        mPlans = plans;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(LayoutInflater.from(mContext),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        holder.bind(mPlans.get(position),position);
    }

    public void setPlans(ArrayList<Plan> plans){
        mPlans = plans;
    }

    @Override
    public int getItemCount() {
        Toast.makeText(mContext,""+mPlans.size(),Toast.LENGTH_SHORT).show();
        return mPlans.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {

    }


    public class PlanViewHolder extends RecyclerView.ViewHolder{

        private TextView mTVTitle, mTVName, mTVAddress, mTVSummary;
        private Button mBTNArrivedTime, mBTNDuration;
        private ImageView mIVPreview;

        public PlanViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_plan, parent, false));

            mTVTitle = itemView.findViewById(R.id.id_item_plan_tv_title);
            mTVName = itemView.findViewById(R.id.id_item_plan_tv_name);
            mTVAddress = itemView.findViewById(R.id.id_item_plan_tv_address);
            mTVSummary = itemView.findViewById(R.id.id_item_plan_tv_summary);
            mBTNArrivedTime = itemView.findViewById(R.id.id_item_plan_btn_arrived_time);
            mBTNDuration = itemView.findViewById(R.id.id_item_plan_btn_duration);
            mIVPreview = itemView.findViewById(R.id.id_item_plan_iv_preview);
        }

        public void bind(Plan plan, int position){

            String title = "Plan " + (position+1);
            String duration = plan.getDuration()+" minutes";

            Glide.with(mContext)
                    .load(plan.getImage_URL())
                    .into(mIVPreview);

            mTVTitle.setText(title);
            mTVName.setText(plan.getName());
            mTVAddress.setText(plan.getAddress());
            mBTNArrivedTime.setText(plan.getArrivedTime());
            mBTNDuration.setText(duration);


        }

    }
}
