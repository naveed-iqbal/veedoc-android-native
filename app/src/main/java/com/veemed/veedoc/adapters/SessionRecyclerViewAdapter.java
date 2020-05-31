package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionRecyclerViewAdapter extends RecyclerView.Adapter<SessionRecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Session> sessions;
    private List<Session> sessionsCopy;

    public SessionRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_history_recyclerview_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.partnerSite.setText(sessions.get(position).getPartnerSite());
        holder.facilityName.setText(sessions.get(position).getFacilityName());
        holder.receivedOn.setText(sessions.get(position).getReceivedOn());
        holder.endpointTextView.setText(sessions.get(position).getEndPointName());
        holder.timeElapsed.setText(sessions.get(position).getDurationInMinutes()+"");
    }

    @Override
    public int getItemCount() {
        if (sessions != null){
            return sessions.size();
        }
        return 0;
    }

    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateSessions(List<Session> sessions){
        if(sessions == null) return;
        this.sessions = sessions;
        this.sessionsCopy = new ArrayList<>(sessions);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }

    @Override
    public Filter getFilter() {
        return sessionsFilter;
    }

    private Filter sessionsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence searchEntered) {
            List<Session> searchedSessions = new ArrayList<>();
            if (searchEntered == null || searchEntered.length() == 0){
                searchedSessions.addAll(sessionsCopy);
            } else {
                String queryString = searchEntered.toString().trim().toLowerCase();
                for (Session session : sessionsCopy){
                    if (session.getPartnerSite().toLowerCase().contains(queryString) ||
                        session.getFacilityName().toLowerCase().contains(queryString) ||
                        session.getReceivedOn().toLowerCase().contains(queryString) ||
                        session.getEndPointName().toLowerCase().contains(queryString) ||
                        session.getDurationInMinutes().toString().toLowerCase().contains(queryString)){
                            searchedSessions.add(session);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = searchedSessions;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            sessions.clear();
            sessions.addAll((List)filterResults.values);

            // determine if we should show no endpoints found
            if (getItemCount() == 0){
                ((FragmentActivity) context).findViewById(R.id.noSessionAvailableTextView).setVisibility(View.VISIBLE);
            } else {
                ((FragmentActivity) context).findViewById(R.id.noSessionAvailableTextView).setVisibility(View.INVISIBLE);
            }

            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView partnerSite, facilityName, receivedOn, endpointTextView, timeElapsed;
        ConstraintLayout sessionOption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionOption = itemView.findViewById(R.id.session_recyclerview_option);
            partnerSite = itemView.findViewById(R.id.partnerSiteContents_sessionHistory);
            facilityName = itemView.findViewById(R.id.facilityNameContents_sessionHistory);
            receivedOn = itemView.findViewById(R.id.receivedOnContents);
            endpointTextView = itemView.findViewById(R.id.endpointContents_sessionHistory);
            timeElapsed = itemView.findViewById(R.id.timeElapsed_sessionHistory);
        }
    }
}
