package com.laacompany.travelplanner.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laacompany.travelplanner.Adapter.ExploreAdapter;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View v){
        mRecyclerView = v.findViewById(R.id.id_fragment_explore_rv_destination_list);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_explore, container,false);


        initView(v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ExploreAdapter exploreAdapter = new ExploreAdapter(getActivity(), Handle.sDestinations);
        mRecyclerView.setAdapter(exploreAdapter);

        return v;
    }


}
