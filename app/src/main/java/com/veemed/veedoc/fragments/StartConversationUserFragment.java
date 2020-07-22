package com.veemed.veedoc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.veemed.veedoc.adapters.ChatUsersAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StartConversationUserFragment extends Fragment implements RecyclerViewListener, View.OnClickListener {
    public static boolean longClicked = false;

    private ChatUsersAdapter adapter;

    private Button btnCancel;

    private Button btnDone;

    private MutableLiveData<List<UserAPIRequest>> chatUsers;

    private List<ChatUsersHolder> conversationsList;

    private LinearLayoutManager layoutManager;

    private LinearLayout llButtons;

    private NavigationActivityViewModel navigationActivityViewModel;

    private RecyclerView rvUsers;

    private TextView tvCenterTextView;

    private View view;

    private void displayNoEndpointsIfNecessary() {
        List<ChatUsersHolder> list = this.conversationsList;
        if (list == null || list.isEmpty()) {
            this.tvCenterTextView.setVisibility(View.VISIBLE);
            return;
        }
        this.tvCenterTextView.setVisibility(View.INVISIBLE);
    }

    private void initObservers() {
        this.chatUsers = this.navigationActivityViewModel.getChatUsers();
        this.chatUsers.observe((LifecycleOwner)this, new Observer<List<UserAPIRequest>>() {
            public void onChanged(List<UserAPIRequest> param1List) {
                StartConversationUserFragment startConversationUserFragment = StartConversationUserFragment.this;
                StartConversationUserFragment.this.conversationsList = (startConversationUserFragment.parseChatUsersFromUsers(param1List));
                StartConversationUserFragment.this.adapter.updateEndpoints(StartConversationUserFragment.this.conversationsList);
                StartConversationUserFragment.this.rvUsers.setItemViewCacheSize(param1List.size());
                StartConversationUserFragment.this.displayNoEndpointsIfNecessary();
            }
        });
        this.navigationActivityViewModel.fetchChatUsers();
    }

    private void initRecyclerView() {
        this.rvUsers = (RecyclerView)this.view.findViewById(R.id.rvUsers);
        this.adapter = new ChatUsersAdapter(getContext(), this);
        this.rvUsers.setAdapter((RecyclerView.Adapter)this.adapter);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.rvUsers.setLayoutManager((RecyclerView.LayoutManager)this.layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.rvUsers.getContext(), 1);
        this.rvUsers.addItemDecoration((RecyclerView.ItemDecoration)dividerItemDecoration);
    }

    private void initViews() {
        this.rvUsers = (RecyclerView)this.view.findViewById(R.id.rvUsers);
        this.tvCenterTextView = (TextView)this.view.findViewById(R.id.tvCenterTextView);
        this.llButtons = (LinearLayout)this.view.findViewById(R.id.llButtons);
        this.btnCancel = (Button)this.view.findViewById(R.id.cancelButtonConversation);
        this.btnDone = (Button)this.view.findViewById(R.id.doneButtonConversation);
        this.llButtons.setVisibility(View.GONE);
        this.btnDone.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
    }

    public static StartConversationUserFragment newInstance() {
        return new StartConversationUserFragment();
    }

    private void resetChecksInList() {
        Iterator<ChatUsersHolder> iterator = this.conversationsList.iterator();
        while (iterator.hasNext())
            ((ChatUsersHolder)iterator.next()).setChecked(false);
    }

    private void toggleMultiSelectUsers(boolean paramBoolean) {
        byte b;
        longClicked = paramBoolean;
        LinearLayout linearLayout = this.llButtons;
        if (paramBoolean) {
            b = 0;
        } else {
            b = 8;
        }
        linearLayout.setVisibility(b);
        this.adapter.notifyDataSetChanged();
    }

    public void itemClicked(View paramView, int paramInt) {}

    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        this.navigationActivityViewModel = (NavigationActivityViewModel)ViewModelProviders.of(getActivity()).get(NavigationActivityViewModel.class);
        initViews();
        initRecyclerView();
        initObservers();
        this.navigationActivityViewModel.setCenterTextViewVisible(false);
    }

    public void onClick(View paramView) {
        int i = paramView.getId();
        if (i != R.id.cancelButtonConversation) {
            if (i != R.id.doneButtonConversation)
                return;
            return;
        }
        resetChecksInList();
        toggleMultiSelectUsers(false);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.start_conversation_users_fragment, paramViewGroup, false);
        longClicked = false;
        return this.view;
    }

    public List<ChatUsersHolder> parseChatUsersFromUsers(List<UserAPIRequest> paramList) {
        ArrayList<ChatUsersHolder> arrayList = new ArrayList();
        Iterator<UserAPIRequest> iterator = paramList.iterator();
        while (iterator.hasNext())
            arrayList.add(new ChatUsersHolder(iterator.next(), false));
        return arrayList;
    }

    public void performAction(String paramString, int paramInt) {
        if ("long_clicked".equalsIgnoreCase(paramString)) {
            toggleMultiSelectUsers(true);
            return;
        }
        if ("start_conversation".equalsIgnoreCase(paramString)) {
            UserAPIRequest userAPIRequest = ((ChatUsersHolder) this.conversationsList.get(paramInt)).user;
        }
    }

    public class ChatUsersHolder {
        boolean checked = false;

        UserAPIRequest user;

        public ChatUsersHolder() {}

        public ChatUsersHolder(UserAPIRequest param1UserAPIRequest, boolean param1Boolean) {
            this.user = param1UserAPIRequest;
            this.checked = param1Boolean;
        }

        public UserAPIRequest getUser() {
            return this.user;
        }

        public boolean isChecked() {
            return this.checked;
        }

        public void setChecked(boolean param1Boolean) {
            this.checked = param1Boolean;
        }

        public void setUser(UserAPIRequest param1UserAPIRequest) {
            this.user = param1UserAPIRequest;
        }
    }
}
