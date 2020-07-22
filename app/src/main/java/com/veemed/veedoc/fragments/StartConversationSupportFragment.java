package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.adapters.SupportGroupAdapter;
import com.veemed.veedoc.models.SupportGroupModel;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import java.util.List;

public class StartConversationSupportFragment extends Fragment implements RecyclerViewListener {
    private SupportGroupAdapter adapter;

    private MutableLiveData<List<SupportGroupModel>> chatUsers;

    private List<SupportGroupModel> conversationsList;

    private LinearLayoutManager layoutManager;

    private NavigationActivityViewModel navigationActivityViewModel;

    private RecyclerView rvUsers;

    private TextView tvCenterTextView;

    private View view;

    private void displayNoEndpointsIfNeccessary() {
        List<SupportGroupModel> list = this.conversationsList;
        if (list == null || list.isEmpty()) {
            this.tvCenterTextView.setVisibility(View.VISIBLE);
            return;
        }
        this.tvCenterTextView.setVisibility(View.INVISIBLE);
    }

    private void initObservers() {
        this.chatUsers = this.navigationActivityViewModel.getChatSupportGroups();
        this.chatUsers.observe((LifecycleOwner)this, new Observer<List<SupportGroupModel>>() {
            public void onChanged(List<SupportGroupModel> param1List) {
                StartConversationSupportFragment.this.conversationsList = param1List;
                StartConversationSupportFragment.this.adapter.updateEndpoints(StartConversationSupportFragment.this.conversationsList);
                StartConversationSupportFragment.this.displayNoEndpointsIfNeccessary();
            }
        });
        this.navigationActivityViewModel.fetchChatSupportGroups();
    }

    private void initRecyclerView() {
        this.rvUsers = (RecyclerView)this.view.findViewById(R.id.support_recyclerView);
        this.adapter = new SupportGroupAdapter(getContext(), this);
        this.rvUsers.setAdapter((RecyclerView.Adapter)this.adapter);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.rvUsers.setLayoutManager((RecyclerView.LayoutManager)this.layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.rvUsers.getContext(), 1);
        this.rvUsers.addItemDecoration((RecyclerView.ItemDecoration)dividerItemDecoration);
    }

    public static StartConversationSupportFragment newInstance() {
        return new StartConversationSupportFragment();
    }

    public void itemClicked(View paramView, int paramInt) {}

    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        this.navigationActivityViewModel = (NavigationActivityViewModel)ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);
        this.rvUsers = (RecyclerView)this.view.findViewById(R.id.support_recyclerView);
        this.tvCenterTextView = (TextView)this.view.findViewById(R.id.tvCenterTextView);
        initRecyclerView();
        initObservers();
        this.navigationActivityViewModel.setCenterTextViewVisible(false);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.start_conversation_support_fragment, paramViewGroup, false);
        return this.view;
    }

    public void performAction(String paramString, int paramInt) {}
}
