package com.laacompany.travelplanner;

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
import com.laacompany.travelplanner.ModelClass.Destination;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_explore, container,false);

        ArrayList<Destination> des = new ArrayList<>();
        des.add(new Destination("Majapahit", "A", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        des.add(new Destination("Majapahit", "B", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        des.add(new Destination("Majapahit", "V", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        des.add(new Destination("Kuningan", "D", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));
        des.add(new Destination("Kuningan2", "C", "Indonesia", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", "https://www.kostjakarta.net/wp-content/uploads/2020/02/Venus-1-scaled.jpg", 3.7F, 1000, 20, 100, 300, 120, 240));

        mRecyclerView = v.findViewById(R.id.id_fragment_explore_rv_destination_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ExploreAdapter exploreAdapter = new ExploreAdapter(getActivity(), des);
        mRecyclerView.setAdapter(exploreAdapter);

        return v;
    }


}
