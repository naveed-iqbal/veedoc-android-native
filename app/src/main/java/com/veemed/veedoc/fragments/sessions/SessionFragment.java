package com.veemed.veedoc.fragments.sessions;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.fragments.CurrentSessionsInnerFragment;
import com.veemed.veedoc.models.User;
import com.veemed.veedoc.utils.ReturnResponse;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.google.android.material.button.MaterialButton;
import com.veemed.veedoc.viewmodels.SessionsViewModel;

public class SessionFragment extends Fragment {
    private NavigationActivityViewModel navigationActivityViewModel;
    private SessionsViewModel sessionsViewModel;
    private MaterialButton innerSessionsButton, sessionsHistoryButton;
    private FrameLayout innerSessionsFragBox;

    public static SessionFragment newInstance() {
        return new SessionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.session_fragment, container, false);
        initWidgets(view);
        displayStartingInnerFragmentScreen();
        setClickers();
        return view;
    }

    private void setClickers() {
        innerSessionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(innerSessionsFragBox.getId(), CurrentSessionsInnerFragment.newInstance(), "OPENED_FRAG")
                        .commit();
            }
        });

        sessionsHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(innerSessionsFragBox.getId(), SessionsHistoryFragment.newInstance(), "OPENED_FRAG")
                        .commit();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        sessionsViewModel = ViewModelProviders.of(getActivity()).get(SessionsViewModel.class);
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to false
        navigationActivityViewModel.setCenterTextViewVisible(false);

        sessionsViewModel.fetchUser().observe(this, new Observer<ReturnResponse<User>>() {
            @Override
            public void onChanged(ReturnResponse<User> user) {
                navigationActivityViewModel.setToolbarTitle(user.getReturnObject().getLastName() + ", " + user.getReturnObject().getFirstInitial());
                navigationActivityViewModel.setToolbarSubtitle(user.getReturnObject().getSpecialistInformation().getPracticeGroup());
            }
        });

    }

    private void initWidgets(View view) {
        innerSessionsButton = view.findViewById(R.id.sessionsButton);
        sessionsHistoryButton = view.findViewById(R.id.sessionHistoryButton);
        innerSessionsFragBox = view.findViewById(R.id.sessions_inner_fragBox);

    }

    private void displayStartingInnerFragmentScreen() {
        Fragment fragmentToOpen = null;
        if (innerSessionsButton.isChecked()){
            fragmentToOpen = CurrentSessionsInnerFragment.newInstance();
        } else if (sessionsHistoryButton.isChecked()){
            fragmentToOpen = SessionsHistoryFragment.newInstance();
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(innerSessionsFragBox.getId(), fragmentToOpen, "OPENED_FRAG").commit();
    }
}
