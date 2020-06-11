package com.veemed.veedoc.adapters;

import android.view.View;

public interface RecyclerViewListener {

    // this implementation of recyclerview clicking was found at: https://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview

    public void itemClicked(View view, int position);

    default public void performAction(String actionType, int requestCode) {

    }
}

