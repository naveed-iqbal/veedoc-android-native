package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatFragment extends Fragment {

    private NavigationActivityViewModel navigationActivityViewModel;
    private FloatingActionButton newMessageFloatingAction;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.chat_fragment, container, false);
        initWidgets(view);
        setClickers(view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);

    }

    private void setClickers(View view) {
        newMessageFloatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_box, StartConversationFragment.newInstance(), "OPENED_FRAG").addToBackStack(null)
                        .commit();

            }
        });
    }

    private void initWidgets(View view) {
        newMessageFloatingAction = view.findViewById(R.id.newMessageFloatingActionButton);

    }

}
