package com.veemed.veedoc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.veemed.CallActionsModel;
import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.DeferCallActivity;
import com.veemed.veedoc.activities.KartCallActivity;
import com.veemed.veedoc.activities.MessagesActivity;
import com.veemed.veedoc.activities.widgets.CallReconnectDialog;
import com.veemed.veedoc.adapters.PendingSessionsRecyclerViewAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.CurrentSessionsViewModel;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class CurrentSessionsInnerFragment extends Fragment implements RecyclerViewListener {
    private Handler handler = new Handler();

    private LinearLayoutManager layoutManager;

    private NavigationActivityViewModel navigationActivityViewModel;

    private TextView noVirtualSessionTextView;

    private List<PendingSession> pendingSessions = new ArrayList<PendingSession>();

    private PendingSessionsRecyclerViewAdapter pendingSessionsRecyclerViewAdapter;

    private RecyclerView rvCurrentSessions;

    private CurrentSessionsViewModel sessionsViewModel;

    private View view;

    private void acceptCall(int paramInt) {
        CallActionsModel callActionsModel = new CallActionsModel();
        callActionsModel.setPerformedBy(Integer.valueOf(1));
        callActionsModel.setPerformedAction("Accepted");
        callActionsModel.setSpecialistRequestId(((PendingSession)this.pendingSessions.get(paramInt)).getId());
        VeeDocRepository.getInstance().acceptCall(callActionsModel, new RetrofitCallbackListener<SessionInfo>() {
            public void onFailure(Call<SessionInfo> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<SessionInfo> param1Call, Response<SessionInfo> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    CurrentSessionsInnerFragment.this.connectCall((SessionInfo)param1Response.body());
            }
        },  0);
    }

    private void deferCall(int paramInt) {
        Intent intent = new Intent(getContext(), DeferCallActivity.class);
        intent.putExtra("session", (Serializable)this.pendingSessions.get(paramInt));
        startActivity(intent);
    }

    private void displayCallReconnectAlert(final int position) {
        PendingSession pendingSession = this.pendingSessions.get(position);
        CallReconnectDialog callReconnectDialog = new CallReconnectDialog(getContext(), pendingSession);
        callReconnectDialog.onReconnect(new View.OnClickListener() {
            public void onClick(View param1View) {
                CurrentSessionsInnerFragment.this.reconnect(position);
            }
        });
        callReconnectDialog.onCancel(new View.OnClickListener() {
            public void onClick(View param1View) {
                CurrentSessionsInnerFragment.this.rejectCall(position);
            }
        });
        callReconnectDialog.show();
    }

    private void displayNoEndpointsIfNeccessary() {
        List<PendingSession> list = this.pendingSessions;
        if (list == null || list.isEmpty()) {
            this.noVirtualSessionTextView.setVisibility(View.VISIBLE);
            return;
        }
        this.noVirtualSessionTextView.setVisibility(View.INVISIBLE);
    }

    private void init() {
        this.noVirtualSessionTextView = (TextView)this.view.findViewById(R.id.noVirtualSessionTextView);
        this.rvCurrentSessions = (RecyclerView)this.view.findViewById(R.id.virtualSessionsPendingRecyclerView);
        this.pendingSessionsRecyclerViewAdapter = new PendingSessionsRecyclerViewAdapter(getContext(), this);
        this.rvCurrentSessions.setAdapter((RecyclerView.Adapter)this.pendingSessionsRecyclerViewAdapter);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.rvCurrentSessions.setLayoutManager((RecyclerView.LayoutManager)this.layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.rvCurrentSessions.getContext(), 1);
        this.rvCurrentSessions.addItemDecoration((RecyclerView.ItemDecoration)dividerItemDecoration);
    }

    public static CurrentSessionsInnerFragment newInstance() {
        return new CurrentSessionsInnerFragment();
    }



    private void openDeferMessages(int paramInt) {
        Intent intent = null;
        PendingSession pendingSession = this.pendingSessions.get(paramInt);
        Conversation conversation = (Conversation)Utility.deferredConversations.get(pendingSession.getId());
        if (conversation != null) {
            intent = new Intent(getContext(), MessagesActivity.class);
            intent.putExtra("conversation", (Serializable)conversation);
            startActivity(intent);
            return;
        }
        this.sessionsViewModel.getDeferredMessageSession().observe((LifecycleOwner)this, new Observer<Conversation>() {
            public void onChanged(Conversation param1Conversation) {
                Intent intent = new Intent(CurrentSessionsInnerFragment.this.getContext(), MessagesActivity.class);
                intent.putExtra("conversation", (Serializable)param1Conversation);
                CurrentSessionsInnerFragment.this.startActivity(intent);
            }
        });
        this.sessionsViewModel.fetchDeferredMessageSession(pendingSession.getId().intValue());
    }

    private void reconnect(int paramInt) {
        this.sessionsViewModel.getSessionInfo().observe((LifecycleOwner)this, new Observer<SessionInfo>() {
            public void onChanged(SessionInfo param1SessionInfo) {
                CurrentSessionsInnerFragment.this.connectCall(param1SessionInfo);
            }
        });
        this.sessionsViewModel.fetchSessionInfo(((PendingSession)this.pendingSessions.get(paramInt)).getId().intValue());
    }

    private void rejectCall(int paramInt) {
        CallActionsModel callActionsModel = new CallActionsModel();
        callActionsModel.setPerformedBy(Integer.valueOf(1));
        callActionsModel.setPerformedAction("Rejected");
        callActionsModel.setSpecialistRequestId(((PendingSession)this.pendingSessions.get(paramInt)).getId());
        VeeDocRepository.getInstance().rejectCall(callActionsModel, new RetrofitCallbackListener<Void>() {
            public void onFailure(Call<Void> param1Call, Throwable param1Throwable, int param1Int) {}

            public void onResponse(Call<Void> param1Call, Response<Void> param1Response, int param1Int) {
                if (param1Response.isSuccessful())
                    Toast.makeText(CurrentSessionsInnerFragment.this.getContext(), "Session closed", com.veemed.veedoc.utils.Toast.LENGTH_LONG).show();
            }
        }, 0);
    }

    private void startScheduledRepeatRefresh() {
        this.handler.postDelayed(new Runnable() {
            public void run() {
                CurrentSessionsInnerFragment.this.sessionsViewModel.fetchPendingSessions();
                CurrentSessionsInnerFragment.this.handler.postDelayed(this, Utility.refreshDelay);
            }
        }, Utility.refreshDelay);
    }

    private void stopScheduledRepeatRefresh() {
        this.handler.removeCallbacksAndMessages(null);
    }

    public void connectCall(SessionInfo paramSessionInfo) {
        Intent intent = new Intent(getContext(), KartCallActivity.class);
        intent.putExtra("session", (Serializable)paramSessionInfo);
        getContext().startActivity(intent);
    }

    @Override
    public void itemClicked(View view, int position) {
        int id = view.getId();
        CallActionsModel actionsModel;
        switch (id) {
            case R.id.btnACcept:
            case R.id.btnStart:
                acceptCall(position);
                break;
            case R.id.btnReject:
            case R.id.btnCancel:
                rejectCall(position);
                break;
            case R.id.btnDefer:
                deferCall(position);
                break;
            case R.id.btnMessage:
                openDeferMessages(position);
                break;
        }
    }

    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        this.navigationActivityViewModel = (NavigationActivityViewModel)ViewModelProviders.of(this).get(NavigationActivityViewModel.class);
        this.sessionsViewModel = (CurrentSessionsViewModel)ViewModelProviders.of(getActivity()).get(CurrentSessionsViewModel.class);
        this.navigationActivityViewModel.setCenterTextViewVisible(true);
        this.sessionsViewModel.getPendingSessions().observe((LifecycleOwner)this, new Observer<List<PendingSession>>() {
            public void onChanged(List<PendingSession> param1List) {
                CurrentSessionsInnerFragment.this.pendingSessions = param1List;
                CurrentSessionsInnerFragment.this.pendingSessionsRecyclerViewAdapter.updateEndpoints(param1List);
                CurrentSessionsInnerFragment.this.displayNoEndpointsIfNeccessary();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        startScheduledRepeatRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopScheduledRepeatRefresh();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.current_sessions_inner_fragment, paramViewGroup, false);
        init();
        return this.view;
    }

    public void performAction(String paramString, int paramInt) {
        if ("DISPLAY_CALL_RECONNECT_ALERT".equalsIgnoreCase(paramString)) {
            stopScheduledRepeatRefresh();
            displayCallReconnectAlert(paramInt);
        }
    }
}
