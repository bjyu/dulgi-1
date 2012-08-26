package com.y2util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AndroidUtil {
	
	static public void showToast (Context context, String str) {
    	Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
	
	static public void showToast (Context context, int value) {
    	String s = String.format("%d", value);
    	showToast (context, s);
    }
	
	static public void showToast (Context context, float value) {
    	String s = String.format("%f", value);
    	showToast (context, s);
    }
	
	static public void showToast (Context context, boolean value) {
    	String s = value ? "true" : "false";
    	showToast (context, s);
    }
	
	static public void showLogI (String title, String msg) {
		Log.i(title, msg);
	}
	
	static public void showLogI (String title, int msg) {
		String s = String.format("%d", msg);
		showLogI(title, s);
	}
	
	static public void showLogI (String title, long msg) {
		String s = String.format("%d", msg);
		showLogI(title, s);
	}
	
	static public void showLogI (String title, float msg) {
		String s = String.format("%f", msg);
		showLogI(title, s);
	}
	
	static public void showLogE (String title, String msg) {
		Log.e(title, " ++++++++++++++++++++++++++++++ " + msg);
	}
}
