package com.veemed.veedoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.fragments.StartConversationUserFragment;
import java.util.ArrayList;
import java.util.List;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.ViewHolder> {
    private static RecyclerViewListener recyclerViewListener;

    private List<StartConversationUserFragment.ChatUsersHolder> chatUserCopy;

    private List<StartConversationUserFragment.ChatUsersHolder> chatUsers;

    private Context context;

    public ChatUsersAdapter(Context paramContext, RecyclerViewListener paramRecyclerViewListener) {
        this.context = paramContext;
        recyclerViewListener = paramRecyclerViewListener;
    }

    public int getItemCount() {
        List<StartConversationUserFragment.ChatUsersHolder> list = this.chatUsers;
        return (list != null) ? list.size() : 0;
    }

    public void onBindViewHolder(@NonNull ViewHolder paramViewHolder, final int position) {
        CheckBox checkBox = paramViewHolder.cbName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((StartConversationUserFragment.ChatUsersHolder)this.chatUsers.get(position)).getUser().getFirstName());
        stringBuilder.append(" ");
        stringBuilder.append(((StartConversationUserFragment.ChatUsersHolder)this.chatUsers.get(position)).getUser().getLastName());
        checkBox.setText(stringBuilder.toString());
        TextView textView = paramViewHolder.tvName;
        stringBuilder = new StringBuilder();
        stringBuilder.append(((StartConversationUserFragment.ChatUsersHolder)this.chatUsers.get(position)).getUser().getFirstName());
        stringBuilder.append(" ");
        stringBuilder.append(((StartConversationUserFragment.ChatUsersHolder)this.chatUsers.get(position)).getUser().getLastName());
        textView.setText(stringBuilder.toString());
        if (StartConversationUserFragment.longClicked) {
            paramViewHolder.cbName.setVisibility(View.VISIBLE);
            paramViewHolder.tvName.setVisibility(View.GONE);
            paramViewHolder.cbName.setChecked(((StartConversationUserFragment.ChatUsersHolder)this.chatUsers.get(position)).isChecked());
            paramViewHolder.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
                    ((StartConversationUserFragment.ChatUsersHolder)ChatUsersAdapter.this.chatUsers.get(position)).setChecked(param1Boolean);
                }
            });
            return;
        }
        paramViewHolder.cbName.setVisibility(View.GONE);
        paramViewHolder.tvName.setVisibility(View.VISIBLE);
        paramViewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                ChatUsersAdapter.recyclerViewListener.performAction("start_conversation", position);
            }
        });
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.chat_users_recycler_view, paramViewGroup, false));
    }

    public void updateEndpoints(List<StartConversationUserFragment.ChatUsersHolder> paramList) {
        this.chatUsers = paramList;
        this.chatUserCopy = new ArrayList<StartConversationUserFragment.ChatUsersHolder>(paramList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        CheckBox cbName;

        TextView tvName;

        public ViewHolder(View param1View) {
            super(param1View);
            this.tvName = (TextView)param1View.findViewById(R.id.tvName);
            this.cbName = (CheckBox)param1View.findViewById(R.id.cbName);
            this.tvName.setOnLongClickListener(this);
        }

        public boolean onLongClick(View param1View) {
            ChatUsersAdapter.recyclerViewListener.performAction("long_clicked", 0);
            return false;
        }
    }
}
