package com.example.customalarm.ui;

import com.example.customalarm.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyAlarmSplitter extends RelativeLayout {

	private TextView dateView;
	private TextView desView;
	private int SplitterType = TODAY;

	public static final int PASSED = -1;
	public static final int BADDAY = 0;
	public static final int TODAY = 1;
	public static final int TOMORROW = 2;
	public static final int ONEWEEK = 3;
	public static final int ONEMONTH = 4;
	public static final int HALFYEAR = 5;
	public static final int ONEYEAR = 6;
	public static final int MORETHANONEYEAR = 7;

	public MyAlarmSplitter(Context context) {
		super(context);
		inflate(context);
	}

	public MyAlarmSplitter(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context);
	}

	public MyAlarmSplitter(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inflate(context);
	}

	private void inflate(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.splitter_my_alarm, this);
		dateView = (TextView) findViewById(R.id.splitterDate);
		desView = (TextView) findViewById(R.id.splitterDesc);
	}

	public void setDate(String text) {
		dateView.setText(text);
	}

	public void setDesc(String text) {
		desView.setText(text);
	}

	public int getSplitterType() {
		return SplitterType;
	}

	public void setSplitterType(int splitterType) {
		SplitterType = splitterType;
	}

	public static String type2Date(int tp) {
		String text = "";
		switch (tp) {
		case TODAY:
			text = "今天";
			break;
		case TOMORROW:
			text = "明天";
			break;
		case ONEWEEK:
			text = "一周内";
			break;
		case ONEMONTH:
			text = "一月内";
			break;
		case HALFYEAR:
			text = "半年内";
			break;
		case ONEYEAR:
			text = "一年内";
			break;
		case MORETHANONEYEAR:
			text = "一年后";
			break;

		default:
			break;
		}
		return text;
	}
}
