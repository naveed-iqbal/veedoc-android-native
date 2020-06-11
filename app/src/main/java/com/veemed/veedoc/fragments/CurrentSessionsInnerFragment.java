package com.veemed.veedoc.fragments;

import android.content.DialogInterface;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.DeferCallActivity;
import com.veemed.veedoc.activities.KartCallActivity;
import com.veemed.veedoc.activities.widgets.CallReconnectDialog;
import com.veemed.veedoc.adapters.PendingSessionsRecyclerViewAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.CurrentSessionsViewModel;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CurrentSessionsInnerFragment extends Fragment implements RecyclerViewListener {

    private NavigationActivityViewModel navigationActivityViewModel;
    private CurrentSessionsViewModel sessionsViewModel;
    private RecyclerView rvCurrentSessions;
    private View view;
    private PendingSessionsRecyclerViewAdapter pendingSessionsRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private Handler handler = new Handler();
    private List<PendingSession> pendingSessions = new ArrayList<>();
    private TextView noVirtualSessionTextView;

    public static CurrentSessionsInnerFragment newInstance() {
        return new CurrentSessionsInnerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current_sessions_inner_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        noVirtualSessionTextView = view.findViewById(R.id.noVirtualSessionTextView);
        rvCurrentSessions = view.findViewById(R.id.virtualSessionsPendingRecyclerView);
        pendingSessionsRecyclerViewAdapter = new PendingSessionsRecyclerViewAdapter(getContext(), this);
        rvCurrentSessions.setAdapter(pendingSessionsRecyclerViewAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        rvCurrentSessions.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCurrentSessions.getContext(), DividerItemDecoration.VERTICAL);
        rvCurrentSessions.addItemDecoration(dividerItemDecoration);


    }

    private void startScheduledRepeatRefresh() {
        handler.postDelayed(new Runnable(){
            public void run(){
                sessionsViewModel.fetchPendingSessions();
                handler.postDelayed(this, Utility.refreshDelay);
            }
        }, Utility.refreshDelay);
    }

    private void displayNoEndpointsIfNeccessary(){
        // if there are no endpoints
        if (pendingSessions == null || pendingSessions.isEmpty()){
            // make no endpoints available message visible
            noVirtualSessionTextView.setVisibility(View.VISIBLE);
        } else {
            noVirtualSessionTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // passing getActivity() to allow communication with navigation activity class
        navigationActivityViewModel = ViewModelProviders.of(this).get(NavigationActivityViewModel.class);
        sessionsViewModel = ViewModelProviders.of(getActivity()).get(CurrentSessionsViewModel.class);

        // set center text view visible to true
        navigationActivityViewModel.setCenterTextViewVisible(true);
        sessionsViewModel.getPendingSessions().observe(this, new Observer<List<PendingSession>>() {
            @Override
            public void onChanged(List<PendingSession> pendingSessions) {
                CurrentSessionsInnerFragment.this.pendingSessions = pendingSessions;
                pendingSessionsRecyclerViewAdapter.updateEndpoints(pendingSessions);
                displayNoEndpointsIfNeccessary();
            }
        });

        startScheduledRepeatRefresh();

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
                break;
        }
    }

    @Override
    public void performAction(String actionType, int position) {
        if("DISPLAY_CALL_CONNECT_ALERT".equalsIgnoreCase(actionType)) {
            displayCallConnectAlert(position);
        }
    }

    private void displayCallConnectAlert(int position) {
        PendingSession pendingSession = pendingSessions.get(position);
        CallReconnectDialog dialog = new CallReconnectDialog(getContext(), pendingSession);
        dialog.onReconnect(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.onCancel(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectCall(position);
            }
        });
        dialog.show();
    }

    private void deferCall(int position) {
        Intent intent = new Intent(getContext(), DeferCallActivity.class);
        intent.putExtra("session", pendingSessions.get(position));
        startActivity(intent);
    }

    private void rejectCall(int position) {
        CallActionsModel actionsModel;
        actionsModel = new CallActionsModel();
        actionsModel.setPerformedBy(1);
        actionsModel.setPerformedAction("Rejected");
        actionsModel.setSpecialistRequestId(pendingSessions.get(position).getId());
        VeeDocRepository.getInstance().rejectCall(actionsModel, new RetrofitCallbackListener<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response, int requestID) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Session closed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t, int requestID) {

            }
        }, 0);
    }

    private void acceptCall(int position) {
        CallActionsModel actionsModel;
        actionsModel = new CallActionsModel();
        actionsModel.setPerformedBy(1);
        actionsModel.setPerformedAction("Accepted");
        actionsModel.setSpecialistRequestId(pendingSessions.get(position).getId());
        VeeDocRepository.getInstance().acceptCall(actionsModel, new RetrofitCallbackListener<SessionInfo>() {
            @Override
            public void onResponse(Call<SessionInfo> call, Response<SessionInfo> response, int requestID) {
                if(response.isSuccessful()) {
                    SessionInfo apiResponse = response.body();
                    Intent intent = new Intent(getContext(), KartCallActivity.class);
                    intent.putExtra("session", apiResponse);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<SessionInfo> call, Throwable t, int requestID) {

            }
        }, 0);
    }
}
