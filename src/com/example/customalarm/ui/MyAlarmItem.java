package com.example.customalarm.ui;

import com.example.customalarm.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * @author Pan
 * 
 */
public class MyAlarmItem extends RelativeLayout {

	public MyAlarmItem(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public MyAlarmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_my_alarm, this);
	}

	public MyAlarmItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

}
