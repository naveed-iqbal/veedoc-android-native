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
    private List<Message> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        conversation = (Conversation) getIntent().getSerializableExtra("conversation");
        init();

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
        viewModel.getMessagesLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.updateEndpoints(messages);
                rvMessages.scrollToPosition(messages.size());
            }
        });

        viewModel.fetchMessages(conversation.getRequestMsgSessionId());


        viewModel.getMessageLiveData().observe(this, new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                adapter.updateEndpoints(message);
            }
        });
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
}
