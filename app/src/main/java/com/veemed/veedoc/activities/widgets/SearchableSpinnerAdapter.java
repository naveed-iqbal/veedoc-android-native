package com.veemed.veedoc.activities.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import java.util.ArrayList;
import java.util.List;

public class SearchableSpinnerAdapter extends RecyclerView.Adapter<SearchableSpinnerAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<KeyValuePair> data;
    private List<KeyValuePair> dataCopy;
    private static RecyclerViewListener recyclerViewListener;

    public SearchableSpinnerAdapter(Context context, RecyclerViewListener recyclerViewListener, List<KeyValuePair> pairs) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
        this.data = pairs;
        this.dataCopy = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public SearchableSpinnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiselect_recyclerview_option, parent, false);
        SearchableSpinnerAdapter.ViewHolder viewHolder = new SearchableSpinnerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchableSpinnerAdapter.ViewHolder holder, int position) {
        holder.item.setText(data.get(position).getKey());
        holder.item.setChecked(data.get(position).isChecked());
        holder.etLicenseNumber.setText(data.get(position).getLicensedStateNumber());

        if(holder.item.isChecked()) {
            holder.etLicenseNumber.setVisibility(View.VISIBLE);
        } else {
            holder.etLicenseNumber.setVisibility(View.GONE);
        }

        holder.etLicenseNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                data.get(position).setLicensedStateNumber(editable.toString());
            }
        });

        holder.item.setOnCheckedChangeListener((compoundButton, b) -> {
            data.get(position).setChecked(b);
            if(holder.item.isChecked()) {
                holder.etLicenseNumber.setVisibility(View.VISIBLE);
            } else {
                holder.etLicenseNumber.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (data != null){
            return data.size();
        }
        return 0;
    }

    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateEndpoints(List<KeyValuePair> endpoints){
        this.data = endpoints;
        dataCopy = new ArrayList<>(endpoints);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox item;
        private EditText etLicenseNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.cbItem);
            etLicenseNumber = itemView.findViewById(R.id.etLicense);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence searchEntered) {
            List<KeyValuePair> searchedSessions = new ArrayList<>();
            if (searchEntered == null || searchEntered.length() == 0){
                searchedSessions.addAll(dataCopy);
            } else {
                String queryString = searchEntered.toString().trim().toLowerCase();
                for (KeyValuePair session : dataCopy){
                    if (session.getKey().toLowerCase().contains(queryString)){
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
            data.clear();
            if(filterResults.values != null)
                data.addAll((List)filterResults.values);

            notifyDataSetChanged();

        }
    };
}