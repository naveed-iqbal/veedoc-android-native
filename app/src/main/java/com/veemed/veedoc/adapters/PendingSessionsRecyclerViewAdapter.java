package com.veemed.veedoc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.R;
import com.veemed.veedoc.activities.KartCallActivity;
import com.veemed.veedoc.models.CallAcceptAPIResponse;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.repositories.VeeDocUserRepository;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PendingSessionsRecyclerViewAdapter extends RecyclerView.Adapter<PendingSessionsRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<PendingSession> pendingSessions;
    private List<PendingSession> pendingSessionsCopy;
    private static RecyclerViewListener recyclerViewListener;

    public PendingSessionsRecyclerViewAdapter(Context context, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_sessions_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSpecialityName.setText(pendingSessions.get(position).getSpecialityName());
        holder.tvDate.setText(new Date().toString());
        holder.tvReason.setText(pendingSessions.get(position).getReasonForRequest());
        if (pendingSessions.get(position).getStatus().equalsIgnoreCase("Pending")){
            holder.llActions.setVisibility(View.VISIBLE);
            holder.llDeferredActions.setVisibility(View.GONE);
            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptCall(position);
                }
            });
            holder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rejectCall(position);
                }
            });
            holder.btnDefer.setOnClickListener(this);
        } else if (pendingSessions.get(position).getStatus().equalsIgnoreCase("Deferred")){
            holder.llActions.setVisibility(View.VISIBLE);
            holder.llDeferredActions.setVisibility(View.GONE);

            holder.btnStart.setOnClickListener(this);
            holder.btnCancel.setOnClickListener(this);
            holder.btnMessage.setOnClickListener(this);
        } else {
            holder.llActions.setVisibility(View.GONE);
            holder.llDeferredActions.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (pendingSessions != null){
            return pendingSessions.size();
        }
        return 0;
    }

    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateEndpoints(List<PendingSession> pendingSessions){
        this.pendingSessions = pendingSessions;
        pendingSessionsCopy = new ArrayList<>(pendingSessions);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnACcept:
                break;
            case R.id.btnReject:
                break;
            case R.id.btnDefer:
                break;
            case R.id.btnStart:
                break;
            case R.id.btnCancel:
                break;
            case R.id.btnMessage:
                break;
        }
    }

    private void acceptCall(int position) {
        CallActionsModel actionsModel = new CallActionsModel();
        actionsModel.setPerformedBy(1);
        actionsModel.setPerformedAction("Accepted");
        actionsModel.setSpecialistRequestId(pendingSessions.get(position).getId());
        VeeDocUserRepository.getInstance().acceptCall(actionsModel, new RetrofitCallbackListener<SessionInfo>() {
            @Override
            public void onResponse(Call<SessionInfo> call, Response<SessionInfo> response, int requestID) {
                if(response.isSuccessful()) {
                    SessionInfo apiResponse = response.body();
                    Intent intent = new Intent(context, KartCallActivity.class);
                    intent.putExtra("session", apiResponse);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<SessionInfo> call, Throwable t, int requestID) {

            }
        }, 0);
    }

    private void rejectCall(int position) {
        CallActionsModel actionsModel = new CallActionsModel();
        actionsModel.setPerformedBy(1);
        actionsModel.setPerformedAction("Rejected");
        actionsModel.setSpecialistRequestId(pendingSessions.get(position).getId());
        VeeDocUserRepository.getInstance().rejectCall(actionsModel, new RetrofitCallbackListener<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response, int requestID) {
                if(response.isSuccessful()) {
                    Toast.makeText(context, "Session closed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t, int requestID) {

            }
        }, 0);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvSpecialityName, tvDate, tvReason;
        LinearLayout llActions, llDeferredActions;
        Button btnAccept, btnReject, btnDefer, btnStart, btnCancel;
        ImageView btnMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpecialityName = itemView.findViewById(R.id.etSpecialityName);
            tvDate = itemView.findViewById(R.id.etDate);
            tvReason = itemView.findViewById(R.id.etReasonForRequest);
            llActions = itemView.findViewById(R.id.llActions);
            llDeferredActions = itemView.findViewById(R.id.llDeferActions);
            btnAccept = itemView.findViewById(R.id.btnACcept);
            btnReject = itemView.findViewById(R.id.btnReject);
            btnDefer = itemView.findViewById(R.id.btnDefer);
            btnStart = itemView.findViewById(R.id.btnStart);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnMessage = itemView.findViewById(R.id.btnMessage);
        }

        @Override
        public void onClick(View view) {
            recyclerViewListener.itemClicked(view, getAdapterPosition());
        }
    }
}
