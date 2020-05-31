package com.veemed.veedoc.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.MoreRecyclerViewAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;

import java.util.ArrayList;

public class MoreFragment extends androidx.fragment.app.Fragment implements RecyclerViewListener {
    private NavigationActivityViewModel navigationActivityViewModel;
    private MoreRecyclerViewAdapter moreRecyclerViewAdapter;
    private RecyclerView moreRecyclerView;
    private ArrayList<Drawable> images = new ArrayList<>();
    private ArrayList<String> optionsNames = new ArrayList<>();

    public static MoreFragment newInstance(){
        return new MoreFragment();
    }

    @Override
    // this is only run when fragment is first created
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize the lists (constant for every time fragment is created)
        initLists();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_fragment, container, false);
        initializeRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("More");
        navigationActivityViewModel.setToolbarSubtitle(null);
    }

    @Override
    public void itemClicked(View view, int position) {
        switch (position){
            case 0:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_box, ProfileManagementFragment.newInstance(), "OPENED_FRAG").addToBackStack(null)
                        .commit();
                break;
            case 1:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_box, MyOffDaysFragment.newInstance(), "OPENED_FRAG").addToBackStack(null)
                        .commit();
                break;
            case 2:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_box, ChangePasswordFragment.newInstance(), "OPENED_FRAG").addToBackStack(null)
                        .commit();
                break;
        }

    }

    private void initializeRecyclerView(View view) {

        moreRecyclerViewAdapter = new MoreRecyclerViewAdapter(getContext(), this, optionsNames, images);
        moreRecyclerView = view.findViewById(R.id.more_recyclerView);
        moreRecyclerView.setAdapter(moreRecyclerViewAdapter);
        moreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(moreRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        moreRecyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void initLists() {
        images.add(getResources().getDrawable(R.drawable.ic_person_outline_black_24dp));
        images.add(getResources().getDrawable(R.drawable.ic_event_busy_black_24dp));
        images.add(getResources().getDrawable(R.drawable.ic_lock_black_24dp));

        optionsNames.add("Profile");
        optionsNames.add("My Off Days");
        optionsNames.add("Change Password");

    }

}
