package com.veemed.veedoc.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.veemed.veedoc.R;

public class ProgressDialog  extends android.app.ProgressDialog {

    public ProgressDialog(Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setMessage("Please wait...");
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void show(String message) {
        setMessage(message);
        super.show();
    }

}
