package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.laacompany.travelplanner.Adapter.ExploreAdapter;
import com.laacompany.travelplanner.Handle.Handle;
import com.laacompany.travelplanner.PickerDialog.DialogDuration;
import com.laacompany.travelplanner.PickerDialog.FilterDialog;
import com.laacompany.travelplanner.PickerDialog.SortDialog;

public class ExploreActivity extends AppCompatActivity implements FilterDialog.FilterDialogListener, SortDialog.SortDialogListener {

    private RecyclerView mRecyclerView;
    ExploreAdapter exploreAdapter;
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ExploreActivity.class);

        return intent;
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.id_activity_explore_rv);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        getSupportActionBar().show();
        initView();

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exploreAdapter = new ExploreAdapter(this, Handle.sDestinations, false);
        mRecyclerView.setAdapter(exploreAdapter);

        FloatingActionButton fabSort   = findViewById(R.id.id_activity_explore_fbtn_sort);
        fabSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortDialog dialogSort = new SortDialog();
                dialogSort.show(getSupportFragmentManager(),"sort");

            }
        });


        FloatingActionButton fabFilter = findViewById(R.id.id_activity_explore_fbtn_filter);
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialog dialogFilter = new FilterDialog();
                dialogFilter.show(getSupportFragmentManager(), "filter dialog");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //   menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                exploreAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    @Override
    public void applyFilter(String country, double rating, int visitor) {
        exploreAdapter.getCustomFilter(country,rating,visitor);
    }

    @Override
    public void applySort(String category, String sortBy) {
        exploreAdapter.getCustomSort(category, sortBy);
    }
}