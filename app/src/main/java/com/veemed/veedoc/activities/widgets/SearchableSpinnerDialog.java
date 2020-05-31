package com.veemed.veedoc.activities.widgets;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.RecyclerViewListener;

import java.util.List;

public class SearchableSpinnerDialog extends DialogFragment implements RecyclerViewListener {


    private List<KeyValuePair> items;
    private RecyclerView rvItems;
    private View parentView;
    private SearchableSpinnerAdapter adapter;
    private SearchView searchView;
    public SearchableSpinnerDialog() {

    }

    public SearchableSpinnerDialog(List<KeyValuePair> items) {
        this();
        this.items = items;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        parentView = inflater.inflate(R.layout.activity_searchable_spinner, null, false);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(parentView)
                // Add action buttons
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
        initViews();
        return builder.create();
    }

    private void initViews() {
        rvItems = parentView.findViewById(R.id.rvItems);
        adapter = new SearchableSpinnerAdapter(getContext(), this, items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = linearLayoutManager;
        rvItems.setItemViewCacheSize(items.size());
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setAdapter(adapter);

        initializeSearchView();
    }

    @Override
    public void itemClicked(View view, int position) {

    }

    private void initializeSearchView() {
        searchView = parentView.findViewById(R.id.svMain);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: MOVE ONQUERYTEXTSUBMIT CODE HERE IF YOU WOULD LIKE DYNAMIC FILTERING OF DATA (I.E. AS YOU TYPE)
                // USING ON QUERY TEXT CHANGE TO DEAL WITH DISPLAYING WHOLE LIST WHEN SEARCH VIEW IS EMPTY AS QUERY TEXT SUBMIT DOES NOT ALLOW THIS.
                // SEE: https://stackoverflow.com/questions/13576283/android-searchview-onquerytextlistener-onquerytextsubmit-not-fired-on-empty-quer
                if (searchView.getQuery().length() == 0){
                    adapter.getFilter().filter(newText);
                }

                return false;
            }
        });

    }



}
