package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.DeferredConversationsAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;

public class ChatFragment extends Fragment implements RecyclerViewListener {
    private DeferredConversationsAdapter adapter;

    private LinearLayoutManager layoutManager;

    private NavigationActivityViewModel navigationActivityViewModel;

    private FloatingActionButton newMessageFloatingAction;

    private RecyclerView rvChannels;

    private View view;

    private void initObservers() {}

    private void initRecyclerView() {
        this.rvChannels = (RecyclerView)this.view.findViewById(R.id.rvChat);
        this.adapter = new DeferredConversationsAdapter(getContext(), this);
        this.rvChannels.setAdapter((RecyclerView.Adapter)this.adapter);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.rvChannels.setLayoutManager((RecyclerView.LayoutManager)this.layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.rvChannels.getContext(), 1);
        this.rvChannels.addItemDecoration((RecyclerView.ItemDecoration)dividerItemDecoration);
    }

    private void initWidgets() {
        this.newMessageFloatingAction = (FloatingActionButton)this.view.findViewById(R.id.fabNewMessage);
        this.rvChannels = (RecyclerView)this.view.findViewById(R.id.rvChat);
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    private void setClickers() {
        this.newMessageFloatingAction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                ChatFragment.this.getActivity().getSupportFragmentManager().beginTransaction().replace(2131296489, StartConversationFragment.newInstance(), "OPENED_FRAG").addToBackStack(null).commit();
            }
        });
    }

    public void itemClicked(View paramView, int paramInt) {}

    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        this.navigationActivityViewModel = (NavigationActivityViewModel)ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);
        initWidgets();
        setClickers();
        initRecyclerView();
        initObservers();
        this.navigationActivityViewModel.setCenterTextViewVisible(false);
    }

    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.chat_fragment, paramViewGroup, false);
        return this.view;
    }

    public void performAction(String paramString, int paramInt) {}
}
