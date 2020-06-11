package com.veemed.veedoc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.MessagesActivity;
import com.veemed.veedoc.adapters.DeferredConversationsAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeferredFragment extends Fragment implements RecyclerViewListener {

    private NavigationActivityViewModel navigationActivityViewModel;
    private RecyclerView rvConversations;
    private View view;
    private DeferredConversationsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Handler handler = new Handler();
    private List<Conversation> conversationsList = new ArrayList<>();
    private TextView noVirtualSessionTextView;
    private MutableLiveData<List<Conversation>> conversations;
    private TextView tvCenterTextView;

    public static DeferredFragment newInstance() {
        return new DeferredFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.messages_deferred_fragment, container, false);

        return view;
    }

    private void initObservers() {
        conversations = navigationActivityViewModel.getConversationsLiveData();
        conversations.observe(this, new Observer<List<Conversation>>() {
            @Override
            public void onChanged(List<Conversation> conversations) {
                DeferredFragment.this.conversationsList = conversations;
                adapter.updateEndpoints(conversationsList);
                displayNoEndpointsIfNeccessary();

            }
        });
        navigationActivityViewModel.fetchConversations();
    }

    private void displayNoEndpointsIfNeccessary() {
        // if there are no endpoints
        if (conversationsList == null || conversationsList.isEmpty()){
            // make no endpoints available message visible
            tvCenterTextView.setVisibility(View.VISIBLE);
        } else {
            tvCenterTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initRecyclerView() {
        rvConversations = view.findViewById(R.id.chat_recyclerView);
        adapter = new DeferredConversationsAdapter(getContext(), this);
        rvConversations.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        rvConversations.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvConversations.getContext(), DividerItemDecoration.VERTICAL);
        rvConversations.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);
        tvCenterTextView = view.findViewById(R.id.tvCenter);
        initRecyclerView();
        initObservers();

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(false);

    }

    @Override
    public void itemClicked(View view, int position) {
        int id = view.getId();
        switch (id) {
            case R.id.rlMain:
                startMessagesActivity(position);
                break;
        }
    }

    private void startMessagesActivity(int position) {
        Conversation conversation = conversationsList.get(position);
        Intent intent = new Intent(getContext(), MessagesActivity.class);
        intent.putExtra("conversation", conversation);
        startActivity(intent);
    }
}
