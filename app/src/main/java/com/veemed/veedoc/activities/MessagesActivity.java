package com.veemed.veedoc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.veemed.veedoc.R;
import com.veemed.veedoc.adapters.MessagesAdapter;
import com.veemed.veedoc.adapters.RecyclerViewListener;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.viewmodels.MessagesActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity implements RecyclerViewListener, View.OnClickListener {

    private MessagesActivityViewModel viewModel;
    private Conversation conversation;
    private EditText etNewMessage;
    private ImageView ivSend;
    private RecyclerView rvMessages;

    private MessagesAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Handler handler = new Handler();
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        conversation = (Conversation) getIntent().getSerializableExtra("conversation");
        if(conversation == null) {
            Toast.makeText(this, getResources().getString(R.string.conversation_null), Toast.LENGTH_LONG).show();
            finish();
        } else {
            init();
        }
    }

    private void init() {
        viewModel = ViewModelProviders.of(this).get(MessagesActivityViewModel.class);
        etNewMessage = findViewById(R.id.etNewMessage);
        ivSend = findViewById(R.id.ivSend);
        ivSend.setOnClickListener(this);
        initRecyclerView();
        fetchData();
    }

    private void fetchData() {
        // First time messages
        viewModel.getMessagesLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                updateAdapter(messages);
                startScheduledRepeatRefresh();
            }
        });
        viewModel.fetchMessages(conversation.getRequestMsgSessionId());

        // Pinged messages
        viewModel.getPingMessagesLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                updateAdapter(messages);
            }
        });

        // sent message
        viewModel.getMessageLiveData().observe(this, new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                adapter.updateEndpoints(message);
                etNewMessage.setText("");
                rvMessages.scrollToPosition(messages.size() - 1);
            }
        });


    }

    private void updateAdapter(List<Message> newMessages) {

        if(this.messages == null) {
            this.messages = newMessages;

        } else {
            List<Message> nonUniqueMessages = new ArrayList();
            for(Message m: this.messages) {
                for(int i = 0; i<newMessages.size(); i++) {
                    Message n = newMessages.get(i);
                    if(m.getId()!=null) {
                        if (m.getId().equals(n.getId())) {
                            nonUniqueMessages.add(n);
                        }
                    }
                }
            }

            newMessages.removeAll(nonUniqueMessages);
            this.messages.addAll(newMessages);
        }

        if(newMessages.size()>0) {
            adapter.updateEndpoints(newMessages);
            rvMessages.scrollToPosition(messages.size() - 1);
        }
    }

    private void initRecyclerView() {
        rvMessages = findViewById(R.id.rvMain);
        adapter = new MessagesAdapter(this, this);
        rvMessages.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        rvMessages.setLayoutManager(layoutManager);
    }

    @Override
    public void itemClicked(View view, int position) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivSend:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        String message = etNewMessage.getText().toString();
        if(!message.trim().isEmpty()) {
            viewModel.sendNewMessage(conversation.getRequestMsgSessionId(), message);
        }
    }

    private void startScheduledRepeatRefresh() {
        handler.postDelayed(new Runnable(){
            public void run(){
                viewModel.fetchPingMessages(conversation.getRequestMsgSessionId());
                handler.postDelayed(this, Utility.refreshDelay);
            }
        }, Utility.refreshDelay);
    }
}
