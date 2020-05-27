package com.laacompany.travelplanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.travelplanner.DestinationDetailActivity;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.R;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.DestinationViewHolder> {

    private Context mContext;
    private ArrayList<Destination> mDestinations;

    public ExploreAdapter(Context context, ArrayList<Destination> destinations){
        mContext = context;
        mDestinations = destinations;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DestinationViewHolder(LayoutInflater.from(mContext),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        holder.bind(mDestinations.get(position), position);
    }

    public void setDestinations(ArrayList<Destination> destinations){
        mDestinations = destinations;
    }



    @Override
    public int getItemCount() {
        return mDestinations.size();
    }

    public class DestinationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTVName, mTVAddress, mTVCountry, mTVRating, mTVVisitor, mTVBestTime, mTVOpenTime;
        private ImageView mIVPreview, mIVFlag;
        private int position;

        public DestinationViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_explore, parent, false));

            mTVName = itemView.findViewById(R.id.id_item_explore_tv_name);
            mTVAddress = itemView.findViewById(R.id.id_item_explore_tv_address);
            mTVCountry =  itemView.findViewById(R.id.id_item_explore_tv_country);
            mTVRating = itemView.findViewById(R.id.id_item_explore_tv_rating);
            mTVVisitor = itemView.findViewById(R.id.id_item_explore_tv_visitor);
            mTVOpenTime = itemView.findViewById(R.id.id_item_explore_tv_open_time);
            mTVBestTime = itemView.findViewById(R.id.id_item_explore_tv_best_time);
            mIVPreview = itemView.findViewById(R.id.id_item_explore_iv_preview);
            mIVFlag = itemView.findViewById(R.id.id_item_explore_iv_flag);

            itemView.setOnClickListener(this);
        }

        public void bind(Destination destination, int position){
            this.position = position;
            String rating = destination.getRating()+" / 10";
            String openTime = Handle.getHourFormat(destination.getOpenTime()) + " - " + Handle.getHourFormat(destination.getCloseTime());
            String bestTime = Handle.getHourFormat(destination.getBestTimeStart()) + " - " + Handle.getHourFormat(destination.getBestTimeEnd());

            mTVName.setText(destination.getName());
            mTVAddress.setText(destination.getAddress());
            mTVCountry.setText(destination.getCountry());
            mTVRating.setText(rating);
            mTVVisitor.setText(String.valueOf(destination.getVisitor()));
            mTVOpenTime.setText(openTime);
            mTVBestTime.setText(bestTime);

            Glide.with(mContext)
                    .load(destination.getPreviewURL())
                    .into(mIVPreview);

            Glide.with(mContext)
                    .load(destination.getFlagURL())
                    .into(mIVFlag);

        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(DestinationDetailActivity.newIntent(mContext, position));
        }
    }
}
