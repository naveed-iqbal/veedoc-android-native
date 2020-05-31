package com.veemed.veedoc.utils;

import android.content.Context;
import android.view.Gravity;

public class Toast {
	
	public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;
	public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;

	
	public static android.widget.Toast makeText(Context context, String message, int duration) {
		android.widget.Toast toast = android.widget.Toast.makeText(context, message, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		
		return toast;
	}
	
	public static android.widget.Toast makeText(Context context, int message, int duration) {
		android.widget.Toast toast = android.widget.Toast.makeText(context, message, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		
		return toast;
	}
	
	public static android.widget.Toast makeText(Context context, String message, int duration, boolean isBottom) {
		android.widget.Toast toast = android.widget.Toast.makeText(context, message, duration);
		if(!isBottom) {
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		
		return toast;
	}
	
	public static android.widget.Toast makeText(Context context, int message, int duration, boolean isBottom) {
		android.widget.Toast toast = android.widget.Toast.makeText(context, message, duration);
		if(!isBottom) {
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		
		return toast;
	}
}
