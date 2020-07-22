package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.SupportGroupModel;
import java.util.ArrayList;
import java.util.List;

public class SupportGroupAdapter extends RecyclerView.Adapter<SupportGroupAdapter.ViewHolder> {
    private static RecyclerViewListener recyclerViewListener;

    private List<SupportGroupModel> chatUserCopy;

    private List<SupportGroupModel> chatUsers;

    private Context context;

    public SupportGroupAdapter(Context paramContext, RecyclerViewListener paramRecyclerViewListener) {
        this.context = paramContext;
        recyclerViewListener = paramRecyclerViewListener;
    }

    public int getItemCount() {
        List<SupportGroupModel> list = this.chatUsers;
        return (list != null) ? list.size() : 0;
    }

    public void onBindViewHolder(@NonNull ViewHolder paramViewHolder, int paramInt) {
        paramViewHolder.tvName.setText(((SupportGroupModel)this.chatUsers.get(paramInt)).getName());
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.support_group_recycler_view, paramViewGroup, false));
    }

    public void updateEndpoints(List<SupportGroupModel> paramList) {
        this.chatUsers = paramList;
        this.chatUserCopy = new ArrayList<SupportGroupModel>(paramList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;

        public ViewHolder(View param1View) {
            super(param1View);
            this.tvName = (TextView)param1View.findViewById(R.id.tvName);
        }

        public void onClick(View param1View) {
            SupportGroupAdapter.recyclerViewListener.itemClicked(param1View, ((SupportGroupModel)SupportGroupAdapter.this.chatUsers.get(getLayoutPosition())).getId().intValue());
        }
    }
}
