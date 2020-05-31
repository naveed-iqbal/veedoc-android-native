package com.veemed.veedoc.fragments.sessions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.google.android.material.button.MaterialButton;
import com.veemed.veedoc.viewmodels.SessionsViewModel;

public class SessionsHistoryFragment extends Fragment {

    private NavigationActivityViewModel navigationActivityViewModel;
    private SessionsViewModel sessionsViewModel;
    private MaterialButton lastWeekButton, lastMonthButton, lastYearButton;
    private FrameLayout fragmentTimeFrameBox;

    public static SessionsHistoryFragment newInstance() {
        return new SessionsHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.session_history_inner_fragment, container, false);
        initWidgets(view);
        displayStartingInnerFragmentScreen();
        setClickers();
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
    }

    private void setClickers() {
        lastWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(fragmentTimeFrameBox.getId(), SessionHistoryFragment.newInstance(7), "OPENED_FRAG")
                        .commit();
            }
        });

        lastMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(fragmentTimeFrameBox.getId(), SessionHistoryFragment.newInstance(31), "OPENED_FRAG")
                        .commit();
            }
        });

        lastYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(fragmentTimeFrameBox.getId(), SessionHistoryFragment.newInstance(365), "OPENED_FRAG")
                        .commit();
            }
        });

    }

    private void initWidgets(View view) {
        lastWeekButton = view.findViewById(R.id.lastWeekButton);
        lastMonthButton = view.findViewById(R.id.lastMonthButton);
        lastYearButton = view.findViewById(R.id.lastYearButton);
        fragmentTimeFrameBox = view.findViewById(R.id.sessionHistoryTimeFrameFragmentBox);

    }

    private void displayStartingInnerFragmentScreen() {
        Fragment fragmentToOpen = null;
        if (lastWeekButton.isChecked()){
            fragmentToOpen = SessionHistoryFragment.newInstance(7);
        } else if (lastMonthButton.isChecked()){
            fragmentToOpen = SessionHistoryFragment.newInstance(31);
        } else if (lastYearButton.isChecked()){
            fragmentToOpen = SessionHistoryFragment.newInstance(365);
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentTimeFrameBox.getId(), fragmentToOpen, "OPENED_FRAG").commit();
    }
}
