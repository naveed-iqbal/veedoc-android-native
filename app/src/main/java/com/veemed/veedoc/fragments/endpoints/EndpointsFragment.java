package com.veemed.veedoc.fragments.endpoints;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.KartCallActivity;
import com.veemed.veedoc.adapters.EndpointsRecyclerViewAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.EndpointsViewModel;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;

import java.util.List;

public class EndpointsFragment extends Fragment implements RecyclerViewListener {
    private NavigationActivityViewModel navigationActivityViewModel;
    private EndpointsViewModel endpointsViewModel;
    private EndpointsRecyclerViewAdapter endpointsRecyclerViewAdapter;
    private RecyclerView endpointsRecyclerView;
    private List<Endpoint> endpointsList;
    private TextView noEndpointsMessage;
    private MaterialButton onlineButton, busyButton, offlineButton;
    Handler handler = new Handler();
    int delay = Utility.refreshDelay; //milliseconds

    private androidx.appcompat.widget.SearchView endpointsSearchView;

    private static final String TAG = "EndpointsFragment";

    public static EndpointsFragment newInstance(){
        return new EndpointsFragment();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.endpoints_fragment, container, false);
        noEndpointsMessage = view.findViewById(R.id.noEndpointTextView);
        initButtons(view);
        initializeRecyclerView(view);
        return view;
    }

    private void initButtons(View view) {
        onlineButton = view.findViewById(R.id.onlineButton);
        onlineButton.setChecked(true);
        offlineButton = view.findViewById(R.id.offlineButton);
        busyButton = view.findViewById(R.id.busyButton);
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endpointsViewModel.getEndPoints("online", "null", 0, 10);
            }
        });

        offlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endpointsViewModel.getEndPoints("offline", "null", 0, 10);
            }
        });

        busyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endpointsViewModel.getEndPoints("busy", "null", 0, 10);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // passing getActivity() to allow communication with navigation activity class
        endpointsViewModel =  ViewModelProviders.of(getActivity()).get(EndpointsViewModel.class);
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("Endpoints");
        navigationActivityViewModel.setToolbarSubtitle(null);

        displayNoEndpointsIfNeccessary();

        endpointsViewModel.initEndpoints().observe(this, new Observer<List<Endpoint>>() {
            @Override
            public void onChanged(List<Endpoint> endpoints) {
                endpointsList = endpoints;
                endpointsRecyclerViewAdapter.updateEndpoints(endpointsList);
                displayNoEndpointsIfNeccessary();
            }


        });

        endpointsViewModel.getStatusLiveData().observe(this, new Observer<List<EndpointStatus>>() {
            @Override
            public void onChanged(List<EndpointStatus> endpointStatuses) {
                processData(endpointStatuses);
            }
        });
        startScheduledRepeatRefresh();
        initializeSearchView();
    }

    private void processData(List<EndpointStatus> endpointStatuses) {
        if(endpointsList == null) {

            return;
        }

        for(EndpointStatus es: endpointStatuses) {
            for (int i=0; i<endpointsList.size(); i++) {
                Endpoint ep = endpointsList.get(i);
                if (ep.getId() == es.getEndPointId()) {
                    if (!ep.getKartStatus().equalsIgnoreCase(es.getStatus())) {
                        endpointsList.remove(i);
                    }
                }
            }
        }

        endpointsRecyclerViewAdapter.updateEndpoints(endpointsList);
    }

    private void startScheduledRepeatRefresh() {
        handler.postDelayed(new Runnable(){
            public void run(){
                endpointsViewModel.refreshData();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
    private LinearLayoutManager layoutManager;

    private void initializeRecyclerView(View view) {
        endpointsRecyclerViewAdapter = new EndpointsRecyclerViewAdapter(getContext(), this);
        endpointsRecyclerView = view.findViewById(R.id.endpoints_recyclerView);
        endpointsRecyclerView.setAdapter(endpointsRecyclerViewAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        endpointsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(endpointsRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        endpointsRecyclerView.addItemDecoration(dividerItemDecoration);
        endpointsRecyclerView.setOnScrollListener(scrollChangeListener);
    }

    RecyclerView.OnScrollListener scrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(layoutManager.findLastVisibleItemPosition() == endpointsList.size() -1){
                //bottom of list!
                endpointsViewModel.loadMoreData();
            }
        }
    };


    private void displayNoEndpointsIfNeccessary(){
        // if there are no endpoints
        if (endpointsList == null || endpointsList.isEmpty()){
            // make no endpoints available message visible
            noEndpointsMessage.setVisibility(View.VISIBLE);
        } else {
            noEndpointsMessage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void itemClicked(View view, int sessionId) {
        Intent intent = new Intent(getContext(), KartCallActivity.class);
        intent.putExtra("sessionId", sessionId);
        startActivity(intent);
    }

    private void initializeSearchView() {
        endpointsSearchView = getView().findViewById(R.id.endpoints_searchView);

        endpointsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                endpointsViewModel.getEndPoints(null, query, 0, Integer.MAX_VALUE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    endpointsViewModel.getEndPoints(null, "null", 0, Integer.MAX_VALUE);
                }

                return false;
            }
        });

    }





}
