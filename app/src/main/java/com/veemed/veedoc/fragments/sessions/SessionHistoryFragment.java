package com.veemed.veedoc.fragments.sessions;

import android.os.Bundle;
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

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.SessionRecyclerViewAdapter;
import com.veemed.veedoc.models.Session;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.viewmodels.SessionsViewModel;

import java.util.List;

public class SessionHistoryFragment extends Fragment {

    private NavigationActivityViewModel navigationActivityViewModel;
    private SessionsViewModel sessionsViewModel;
    private SessionRecyclerViewAdapter lastYearSessionRecyclerViewAdapter;
    private RecyclerView lastYearSessionRecyclerView;
    private List<Session> lastYearSessionsList;
    private TextView noSessionsLastYear;
    private androidx.appcompat.widget.SearchView lastYearSessionsSearchView;
    private static int param;

    public static SessionHistoryFragment newInstance(int param) {
        SessionHistoryFragment.param = param;
        return new SessionHistoryFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lastyear_session_history_fragment, container, false);
        initializeRecyclerView(view);
        noSessionsLastYear = view.findViewById(R.id.noSessionAvailableTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // passing getActivity() to allow communication with navigation activity class
        sessionsViewModel = ViewModelProviders.of(getActivity()).get(SessionsViewModel.class);
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);

        displayNoSessionsIfNecessary();

        sessionsViewModel.getLastYearSessions(param).observe(this, new Observer<ReturnResponse<List<Session>>>() {
            @Override
            public void onChanged(ReturnResponse<List<Session>> lastYearSessions) {
                lastYearSessionsList = lastYearSessions.getReturnObject();
                lastYearSessionRecyclerViewAdapter.updateSessions(lastYearSessionsList);
                displayNoSessionsIfNecessary();
            }
        });

        initializeSearchView();

    }

    private void initializeRecyclerView(View view) {
        lastYearSessionRecyclerViewAdapter = new SessionRecyclerViewAdapter(getContext());
        lastYearSessionRecyclerView = view.findViewById(R.id.lastYear_sessionHistoryRecyclerView);
        lastYearSessionRecyclerView.setAdapter(lastYearSessionRecyclerViewAdapter);
        lastYearSessionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lastYearSessionRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        lastYearSessionRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void displayNoSessionsIfNecessary() {
        // if there are no endpoints
        if (lastYearSessionsList == null || lastYearSessionsList.isEmpty()){
            // make no endpoints available message visible
            noSessionsLastYear.setVisibility(View.VISIBLE);
        } else {
            noSessionsLastYear.setVisibility(View.INVISIBLE);
        }
    }

    private void initializeSearchView() {
        lastYearSessionsSearchView = getView().findViewById(R.id.lastyear_session_history_searchView);

        lastYearSessionsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lastYearSessionRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: MOVE ONQUERYTEXTSUBMIT CODE HERE IF YOU WOULD LIKE DYNAMIC FILTERING OF DATA (I.E. AS YOU TYPE)
                // USING ON QUERY TEXT CHANGE TO DEAL WITH DISPLAYING WHOLE LIST WHEN SEARCH VIEW IS EMPTY AS QUERY TEXT SUBMIT DOES NOT ALLOW THIS.
                // SEE: https://stackoverflow.com/questions/13576283/android-searchview-onquerytextlistener-onquerytextsubmit-not-fired-on-empty-quer
                if (lastYearSessionsSearchView.getQuery().length() == 0){
                    lastYearSessionRecyclerViewAdapter.getFilter().filter(newText);
                }

                return false;
            }
        });

    }
}
