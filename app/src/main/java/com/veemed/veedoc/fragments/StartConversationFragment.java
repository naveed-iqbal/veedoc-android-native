package com.veemed.veedoc.fragments;


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

public class StartConversationFragment extends Fragment {
    private NavigationActivityViewModel navigationActivityViewModel;
    private MaterialButton usersButton, supportButton;
    private FrameLayout startConversationInnerFragmentBox;

    public static StartConversationFragment newInstance(){
        return new StartConversationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_conversation_fragment, container, false);
        initWidgets(view);
        displayStartingInnerFragmentScreen();
        setClickers();
        return view;
    }

    private void setClickers() {
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(startConversationInnerFragmentBox.getId(), StartConversationUserFragment.newInstance(), "OPENED_FRAG")
                        .commit();
            }
        });

        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(startConversationInnerFragmentBox.getId(), StartConversationSupportFragment.newInstance(), "OPENED_FRAG")
                        .commit();
            }
        });




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        navigationActivityViewModel.setToolbarTitle("Start Conversation");
        navigationActivityViewModel.setToolbarSubtitle(null);
    }

    private void initWidgets(View view) {
        usersButton = view.findViewById(R.id.userButton);
        supportButton = view.findViewById(R.id.supportButton);
        startConversationInnerFragmentBox = view.findViewById(R.id.startConversationInnerFragmentBox);
    }

    private void displayStartingInnerFragmentScreen() {
        Fragment fragmentToOpen = null;
        if (usersButton.isChecked()){
            fragmentToOpen = StartConversationUserFragment.newInstance();
        } else if (supportButton.isChecked()){
            fragmentToOpen = StartConversationSupportFragment.newInstance();
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(startConversationInnerFragmentBox.getId(), fragmentToOpen, "OPENED_FRAG").commit();

    }
}
