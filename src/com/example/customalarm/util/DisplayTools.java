package com.example.customalarm.util;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

public class DisplayTools {

	public DisplayTools() {
		// TODO 自动生成的构造函数存根
	}
	
	public int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	public int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	public int getWindowHeight(Activity context) {
		return context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
	}

	public static int dip2px(float dp, Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int px2dip(float px, Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

}
