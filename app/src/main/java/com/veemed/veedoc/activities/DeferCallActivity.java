package com.veemed.veedoc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.R;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.DeferResponseModel;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import retrofit2.Call;
import retrofit2.Response;

public class DeferCallActivity extends AppCompatActivity {


    private EditText etMessage;
    private Button btnSend;
    private PendingSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defer_call);

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDeferMessage(etMessage.getText().toString());
            }
        });

        session = (PendingSession) getIntent().getSerializableExtra("session");
    }

    private void sendDeferMessage(String message) {
        if(!message.isEmpty()) {
            CallActionsModel actionsModel;
            actionsModel = new CallActionsModel();
            actionsModel.setPerformedBy(1);
            actionsModel.setPerformedAction("Deferred");
            actionsModel.setSpecialistRequestId(session.getId());
            actionsModel.setComments(message);
            VeeDocRepository.getInstance().deferCall(actionsModel, new RetrofitCallbackListener<Conversation>() {
                @Override
                public void onResponse(Call<Conversation> call, Response<Conversation> response, int requestID) {
                    if(response.isSuccessful()) {
                        performPostAction(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Conversation> call, Throwable t, int requestID) {

                }
            }, 0);
        } else {
            etMessage.setError(getResources().getString(R.string.message_can_not_be_empty));
        }

    }

    private void performPostAction(Conversation conversation) {
        Utility.deferredConversations.put(session.getId(), conversation);
        finish();
    }
}
