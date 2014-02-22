package com.example.customalarm.ui;

import com.example.customalarm.AlarmDetail;
import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.interfase.BaseItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author NashLegend
 * 
 */
public class MyAlarmItem extends RelativeLayout implements BaseItem {
	private TextView clockTextView;
	private TextView tagTextView;
	private TextView descTextView;
	private Button editButton;
	private Alarm alarm;
	private Bundle bundle;

	public MyAlarmItem(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_my_alarm, this);
		clockTextView = (TextView) findViewById(R.id.alarmClock);
		tagTextView = (TextView) findViewById(R.id.alarmtag);
		descTextView = (TextView) findViewById(R.id.alarmtime);
		editButton = (Button) findViewById(R.id.editAlarm);
	}

	public MyAlarmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyAlarmItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setDataBundle(Bundle bundle) {
		this.bundle = bundle;
		this.alarm = new Alarm(getContext().getApplicationContext(), bundle);
		clockTextView.setText(alarm.getTimeDescription());
		tagTextView.setText(alarm.getTag());
		descTextView.setText(alarm.getAlarmDescription());//
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), AlarmDetail.class);
				intent.putExtra("alarm", MyAlarmItem.this.bundle);
				getContext().startActivity(intent);
			}
		});
	}

}
