package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.Endpoint;

import java.util.ArrayList;
import java.util.List;

public class EndpointsRecyclerViewAdapter extends RecyclerView.Adapter<EndpointsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Endpoint> endpoints;
    private List<Endpoint> endpointsCopy;
    private static RecyclerViewListener recyclerViewListener;

    public EndpointsRecyclerViewAdapter(Context context, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.endpoints_recyclerview_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.partnerSite.setText(endpoints.get(position).getPartnerSiteName());
        holder.facilityName.setText(endpoints.get(position).getFacilityName());
        holder.location.setText(endpoints.get(position).getLocation());
        holder.endpointTextView.setText(endpoints.get(position).getName());
        holder.lastSession.setText(endpoints.get(position).getLastSessionTime());
        if (endpoints.get(position).getKartStatus().equalsIgnoreCase("online")){
            // set image to green
            holder.onlineStatusBox.setImageDrawable(context.getResources().getDrawable(R.drawable.filled_square_green));
            holder.connectButton.setVisibility(View.VISIBLE);
            // holder.connectButton.setOnClickListener(view->recyclerViewListener.itemClicked(view, endpoints.get(position).getId()));
        } else if (endpoints.get(position).getKartStatus().equalsIgnoreCase("busy")){
            // set image to green
            holder.onlineStatusBox.setImageDrawable(context.getResources().getDrawable(R.drawable.filled_square_yellow));
            holder.connectButton.setVisibility(View.INVISIBLE);
        } else {
            // set image to gray
            holder.onlineStatusBox.setImageDrawable(context.getResources().getDrawable(R.drawable.filled_square_gray));
            holder.connectButton.setVisibility(View.INVISIBLE);
        }

        // set text depending on if online or not
        holder.onlineStatus.setText(endpoints.get(position).getKartStatus());


    }

    @Override
    public int getItemCount() {
        if (endpoints != null){
            return endpoints.size();
        }
        return 0;
    }

    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateEndpoints(List<Endpoint> endpoints){
        this.endpoints = endpoints;
        endpointsCopy = new ArrayList<>(endpoints);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView partnerSite, facilityName, location, endpointTextView, lastSession, onlineStatus;
        ConstraintLayout endpointsOptions;
        ImageView onlineStatusBox;
        Button connectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            endpointsOptions = itemView.findViewById(R.id.endpoints_option);
            partnerSite = itemView.findViewById(R.id.partnerSiteContents_endpoint);
            facilityName = itemView.findViewById(R.id.facilityNameContents_endpoint);
            location = itemView.findViewById(R.id.locationContents);
            endpointTextView = itemView.findViewById(R.id.endpointContents_endpoint);
            lastSession = itemView.findViewById(R.id.lastSessionContents);
            onlineStatus = itemView.findViewById(R.id.offline_onlineTextView);
            onlineStatusBox = itemView.findViewById(R.id.offline_onlineSquare);
            //itemView.setOnClickListener(this);
            connectButton = itemView.findViewById(R.id.connectButton);
            connectButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewListener.itemClicked(view, endpoints.get(getLayoutPosition()).getId());
        }
    }
}
