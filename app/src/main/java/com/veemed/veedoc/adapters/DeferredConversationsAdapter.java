package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.utils.Utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeferredConversationsAdapter extends RecyclerView.Adapter<DeferredConversationsAdapter.ViewHolder> {

    private Context context;
    private List<Conversation> pendingSessions;
    private List<Conversation> pendingSessionsCopy;
    private static RecyclerViewListener recyclerViewListener;

    public DeferredConversationsAdapter(Context context, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversations_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.etFacilityName.setText(pendingSessions.get(position).getPartnerSiteName());
        try {
            Date date = Utility.SERVER_DATE_FORMAT.parse(pendingSessions.get(position).getReceivedOn());
            holder.etTime.setText(Utility.APP_TIME_FORMAT.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.etName.setText(pendingSessions.get(position).getMessage());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMessagesScreen(view, position);
            }
        });
    }

    private void openMessagesScreen(View view, int position) {
        recyclerViewListener.itemClicked(view, position);
    }

    @Override
    public int getItemCount() {
        if (pendingSessions != null){
            return pendingSessions.size();
        }
        return 0;
    }

    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateEndpoints(List<Conversation> pendingSessions){
        this.pendingSessions = pendingSessions;
        pendingSessionsCopy = new ArrayList(pendingSessions);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }


    private void acceptCall(View view, int position) {
        recyclerViewListener.itemClicked(view, position);

    }

    private void rejectCall(View view, int position) {
        recyclerViewListener.itemClicked(view, position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView etTime, etFacilityName, etName;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etTime = itemView.findViewById(R.id.etTime);
            etFacilityName = itemView.findViewById(R.id.etFacilityName);
            etName = itemView.findViewById(R.id.etName);
            view = itemView.findViewById(R.id.rlMain);
        }

        @Override
        public void onClick(View view) {
            recyclerViewListener.itemClicked(view, getAdapterPosition());
        }
    }
}
