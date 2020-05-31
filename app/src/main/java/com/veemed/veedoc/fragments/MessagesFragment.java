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

public class MessagesFragment extends Fragment {
    private NavigationActivityViewModel navigationActivityViewModel;
    private MaterialButton chatButton, deferredButton;
    private FrameLayout messagesFragBox;

    public static MessagesFragment newInstance(){
        return new MessagesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.messages_fragment, container, false);
        initWidgets(view);
        displayStartingInnerFragmentScreen();
        setClickers();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to false
        navigationActivityViewModel.setCenterTextViewVisible(false);
        navigationActivityViewModel.setToolbarTitle("Messages");
        navigationActivityViewModel.setToolbarSubtitle(null);

    }

    private void setClickers() {
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(messagesFragBox.getId(), ChatFragment.newInstance(), "OPENED_FRAG")
                        .commit();
            }
        });

        deferredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(messagesFragBox.getId(), DeferredFragment.newInstance(), "OPENED_FRAG")
                        .commit();
            }
        });

    }


    private void initWidgets(View view) {
        chatButton = view.findViewById(R.id.chatButton);
        deferredButton = view.findViewById(R.id.deferredButton);
        messagesFragBox = view.findViewById(R.id.frameLayoutMessages);

    }

    private void displayStartingInnerFragmentScreen() {
        Fragment fragmentToOpen = null;
        if (chatButton.isChecked()){
            fragmentToOpen = ChatFragment.newInstance();
        } else if (deferredButton.isChecked()){
            fragmentToOpen = DeferredFragment.newInstance();
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(messagesFragBox.getId(), fragmentToOpen, "OPENED_FRAG").commit();
    }
}
