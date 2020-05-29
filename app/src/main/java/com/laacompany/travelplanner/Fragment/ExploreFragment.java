package com.laacompany.travelplanner.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laacompany.travelplanner.Adapter.ExploreAdapter;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.MainActivity;
import com.laacompany.travelplanner.ModelClass.Destination;
import com.laacompany.travelplanner.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private RecyclerView mRecyclerView;
    Context mContext;
    ExploreAdapter exploreAdapter;


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
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exploreAdapter = new ExploreAdapter(getActivity(), Handle.sDestinations, false);
        mRecyclerView.setAdapter(exploreAdapter);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = new SearchView(getActivity());
        searchItem.setActionView(searchView);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    exploreAdapter.getFilter().filter(newText);
                    return true;
                }
            });


            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
                }
            });

    }
}
