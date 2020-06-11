package com.veemed.veedoc.activities.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.veemed.veedoc.R;
import com.veemed.veedoc.models.PendingSession;

import javax.xml.transform.Templates;

public class CallReconnectDialog extends Dialog implements View.OnClickListener {

    private Button btnConnect, btnCancel;
    private PendingSession session;
    private TextView etTitle, etMessage;

    private View.OnClickListener cancelListener, connectListener;


    public CallReconnectDialog(@NonNull Context context, PendingSession pendingSession) {
        super(context);
        session = pendingSession;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.call_reconnect_dialog);

        // default not cancelable
        setCanceledOnTouchOutside(false);

        btnConnect = (Button) findViewById(R.id.btnStart);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnConnect.setOnClickListener(connectListener);
        btnCancel.setOnClickListener(cancelListener);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
    }

    @Override
    public void onClick(View v) {

    }


    public void onCancel(View.OnClickListener onClickListener) {
        if(btnCancel == null) cancelListener = onClickListener;
        else btnCancel.setOnClickListener(onClickListener);
    }

    public void onReconnect(View.OnClickListener onClickListener) {
        if(btnConnect == null) connectListener = onClickListener;
        else btnConnect.setOnClickListener(onClickListener);
    }

    public void setTitle(String title) {
        etTitle.setText(title);
    }

    public void setMessage(String message) {
        etMessage.setText(message);
    }
}
