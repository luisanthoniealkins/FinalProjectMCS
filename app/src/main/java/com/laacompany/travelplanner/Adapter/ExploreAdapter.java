package com.laacompany.travelplanner.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.travelplanner.DestinationDetailActivity;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.PlanActivity;
import com.laacompany.travelplanner.R;
import com.laacompany.travelplanner.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.DestinationViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Destination> mDestinations, mDestinationsFull;
    private boolean searching;

    public ExploreAdapter(Context context, ArrayList<Destination> destinations, boolean searching){
        mContext = context;
        mDestinations = mDestinationsFull = destinations;
        this.searching = searching;

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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Destination> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDestinationsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Destination destination : mDestinations) {
                    if (destination.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(destination);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDestinations.clear();
            mDestinations.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    public class DestinationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTVName, mTVAddress, mTVCountry, mTVRating, mTVVisitor, mTVOpenTime;
        private ImageView mIVPreview, mIVFlag;
        private String mDestinationID;

        public DestinationViewHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_explore, parent, false));

            mTVName = itemView.findViewById(R.id.id_item_explore_tv_name);
            mTVAddress = itemView.findViewById(R.id.id_item_explore_tv_address);
            mTVCountry =  itemView.findViewById(R.id.id_item_explore_tv_country);
            mTVRating = itemView.findViewById(R.id.id_item_explore_tv_rating);
            mTVVisitor = itemView.findViewById(R.id.id_item_explore_tv_visitor);
            mTVOpenTime = itemView.findViewById(R.id.id_item_explore_tv_open_time);
            mIVPreview = itemView.findViewById(R.id.id_item_explore_iv_preview);
            mIVFlag = itemView.findViewById(R.id.id_item_explore_iv_flag);

            itemView.setOnClickListener(this);
        }

        public void bind(Destination destination, int position){
            mDestinationID = destination.getDestinationId();
            String rating = destination.getRating()+" / 5";
            String openTime = Handle.getHourFormat(destination.getOpenTime()) + " - " + Handle.getHourFormat(destination.getCloseTime());
            String bestTime = Handle.getHourFormat(destination.getBestTimeStart()) + " - " + Handle.getHourFormat(destination.getBestTimeEnd());
            String time = openTime + " , " + bestTime;

            mTVName.setText(destination.getName());
            mTVAddress.setText(destination.getAddress());
            mTVCountry.setText(destination.getCountry());
            mTVRating.setText(rating);
            mTVVisitor.setText(String.valueOf(destination.getTotalVisitor()));
            mTVOpenTime.setText(time);

            Glide.with(mContext)
                    .load(destination.getPreviewUrl())
                    .into(mIVPreview);

            Glide.with(mContext)
                    .load(destination.getFlagUrl())
                    .into(mIVFlag);

        }

        @Override
        public void onClick(View v) {
            if (searching){
                Intent returnIntent = new Intent();
                returnIntent.putExtra(PlanActivity.EXTRA_RESULT_DESTINATION_ID,mDestinationID);
                ((SearchActivity)mContext).setResult(Activity.RESULT_OK,returnIntent);
                ((SearchActivity)mContext).finish();
            } else {
                mContext.startActivity(DestinationDetailActivity.newIntent(mContext, mDestinationID));
            }
        }
    }
}
