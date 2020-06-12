package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.utils.Utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context context;
    private List<Message> messages;
    private List<Message> messagesCopy;
    private static RecyclerViewListener recyclerViewListener;

    public MessagesAdapter(Context context, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dateStr = "";
        try {
            Date date = Utility.SERVER_DATE_FORMAT.parse(messages.get(position).getReceivedOn());
            dateStr = Utility.APP_TIME_FORMAT.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(Utility.user.getId() == messages.get(position).getFromUserId()) {
            holder.etMessage.setText(messages.get(position).getMessage());
            holder.etDate.setText(dateStr);
            setViewsVisibility(holder, true);
        } else {
            holder.etMessageReceived.setText(messages.get(position).getMessage());
            holder.etDateReceived.setText(dateStr);
            setViewsVisibility(holder, false);
        }
    }

    private void setViewsVisibility(ViewHolder holder, boolean isSent) {
        if(isSent) {
            holder.etMessage.setVisibility(View.VISIBLE);
            holder.etDate.setVisibility(View.VISIBLE);
            holder.llBody.setVisibility(View.VISIBLE);

            holder.etMessageReceived.setVisibility(View.GONE);
            holder.etDateReceived.setVisibility(View.GONE);
            holder.llBodyReceived.setVisibility(View.GONE);
        } else {
            holder.etMessage.setVisibility(View.GONE);
            holder.etDate.setVisibility(View.GONE);
            holder.llBody.setVisibility(View.GONE);

            holder.etMessageReceived.setVisibility(View.VISIBLE);
            holder.etDateReceived.setVisibility(View.VISIBLE);
            holder.llBodyReceived.setVisibility(View.VISIBLE);
        }
    }

    private void openMessagesScreen(View view, int position) {
        recyclerViewListener.itemClicked(view, position);
    }

    @Override
    public int getItemCount() {
        if (messages != null){
            return messages.size();
        }
        return 0;
    }

    // IDEA CREDITS: CodingWithMitch MVVM tutorial
    public void updateEndpoints(List<Message> newMessages){
        if(this.messages == null) {
            this.messages = newMessages;
            this.messagesCopy = new ArrayList(newMessages);
        } else {
            //this.messages.addAll(newMessages); already added from MessageActivity due to pass by reference behaviour of JAVA
            this.messagesCopy.addAll(newMessages);
        }
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }

    public void updateEndpoints(Message message){
        this.messages.add(message);
        messagesCopy.add(message);
        notifyDataSetChanged(); // this is so that the adapter will update the view with new data
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView etMessage, etDate;
        LinearLayout llBody;

        TextView etMessageReceived, etDateReceived;
        LinearLayout llBodyReceived;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etMessage = itemView.findViewById(R.id.etMessage);
            etDate = itemView.findViewById(R.id.etDate);
            llBody = itemView.findViewById(R.id.llMessage);

            etMessageReceived = itemView.findViewById(R.id.etMessageReceived);
            etDateReceived = itemView.findViewById(R.id.etDateReceived);
            llBodyReceived = itemView.findViewById(R.id.llMessageReceived);
        }

        @Override
        public void onClick(View view) {
            recyclerViewListener.itemClicked(view, getAdapterPosition());
        }
    }
}
