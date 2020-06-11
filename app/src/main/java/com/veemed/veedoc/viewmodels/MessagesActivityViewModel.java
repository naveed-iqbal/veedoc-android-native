package com.veemed.veedoc.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.models.NewMessageBody;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.utils.Utility;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MessagesActivityViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messagesViewModel = new MutableLiveData<>();
    private MutableLiveData<Message> messageViewModel = new MutableLiveData<>();

    public MutableLiveData<List<Message>> getMessagesLiveData() {
        return messagesViewModel;
    }

    public MutableLiveData<Message> getMessageLiveData() {
        return messageViewModel;
    }

    public void fetchMessages(int conversationId) {
        VeeDocRepository.getInstance().getMessages(conversationId, 0, 100, true, new RetrofitCallbackListener<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response, int requestID) {
                if(response.isSuccessful()) {
                    messagesViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t, int requestID) {

            }
        }, 0);
    }

    public void sendNewMessage(int messageSessionId, String message) {
        NewMessageBody messageBody = new NewMessageBody();
        messageBody.setRequestMsgSessionId(messageSessionId);
        messageBody.setMessage(message);

        VeeDocRepository.getInstance().sendNewMessage(messageBody, new RetrofitCallbackListener<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response, int requestID) {
                if(response.isSuccessful()) {
                    Message msg = new Message();
                    msg.setMessage(message);
                    msg.setFromUserId(Utility.user.getId());
                    msg.setReceivedOn(Utility.SERVER_DATE_FORMAT.format(new Date()));
                    messageViewModel.setValue(msg);
                } else {
                    // TODO DisplayToast
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t, int requestID) {

            }
        }, 0);
    }
}
