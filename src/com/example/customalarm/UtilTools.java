package com.example.customalarm;

import android.content.Context;

public class UtilTools {

	public UtilTools() {
		// TODO 自动生成的构造函数存根
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
