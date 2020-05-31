package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.PendingSessionsRecyclerViewAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.CurrentSessionsViewModel;
import com.veemed.veedoc.viewmodels.EndpointsViewModel;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class CurrentSessionsInnerFragment extends Fragment implements RecyclerViewListener {

    private NavigationActivityViewModel navigationActivityViewModel;
    private CurrentSessionsViewModel sessionsViewModel;
    private RecyclerView rvCurrentSessions;
    private View view;
    private PendingSessionsRecyclerViewAdapter pendingSessionsRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    Handler handler = new Handler();
    private List<PendingSession> pendingSessions = new ArrayList<>();
    private TextView noVirtualSessionTextView;

    public static CurrentSessionsInnerFragment newInstance() {
        return new CurrentSessionsInnerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current_sessions_inner_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        noVirtualSessionTextView = view.findViewById(R.id.noVirtualSessionTextView);
        rvCurrentSessions = view.findViewById(R.id.virtualSessionsPendingRecyclerView);
        pendingSessionsRecyclerViewAdapter = new PendingSessionsRecyclerViewAdapter(getContext(), this);
        rvCurrentSessions.setAdapter(pendingSessionsRecyclerViewAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        rvCurrentSessions.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCurrentSessions.getContext(), DividerItemDecoration.VERTICAL);
        rvCurrentSessions.addItemDecoration(dividerItemDecoration);


    }

    private void startScheduledRepeatRefresh() {
        handler.postDelayed(new Runnable(){
            public void run(){
                sessionsViewModel.fetchPendingSessions();
                handler.postDelayed(this, Utility.refreshDelay);
            }
        }, Utility.refreshDelay);
    }

    private void displayNoEndpointsIfNeccessary(){
        // if there are no endpoints
        if (pendingSessions == null || pendingSessions.isEmpty()){
            // make no endpoints available message visible
            noVirtualSessionTextView.setVisibility(View.VISIBLE);
        } else {
            noVirtualSessionTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(this).get(NavigationActivityViewModel.class);
        sessionsViewModel = ViewModelProviders.of(getActivity()).get(CurrentSessionsViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        sessionsViewModel.getPendingSessions().observe(this, new Observer<List<PendingSession>>() {
            @Override
            public void onChanged(List<PendingSession> pendingSessions) {
                CurrentSessionsInnerFragment.this.pendingSessions = pendingSessions;
                pendingSessionsRecyclerViewAdapter.updateEndpoints(pendingSessions);
                displayNoEndpointsIfNeccessary();
            }
        });

        startScheduledRepeatRefresh();

    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
