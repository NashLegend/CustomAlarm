package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.interfase.BaseItem;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectedAlarmItem extends RelativeLayout implements BaseItem {

	private ImageView imageView;
	private TextView tagTextView;
	private TextView descTextView;
	private TextView numTextView;
	private Button button;
	private Alarm alarm;

	public SelectedAlarmItem(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_selected_alarm, this);
		imageView = (ImageView) findViewById(R.id.Alarm_background);
		tagTextView = (TextView) findViewById(R.id.alarmtag);
		descTextView = (TextView) findViewById(R.id.alarmtime);
		numTextView = (TextView) findViewById(R.id.numAdded);
	}

	public SelectedAlarmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectedAlarmItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void setDataBundle(Bundle bundle) {
		alarm = new Alarm(getContext().getApplicationContext(), bundle);
		//TODO
		imageView.setImageDrawable(null);
		tagTextView.setText(alarm.getTag());
		descTextView.setText(alarm.getAlarmDescription());
		numTextView.setText("NAN");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
				//数据库中添加
				//闹铃列表中添加
				//此处显示已添加（显示对号并隐藏按钮）
				alarm.activate();
			}
		});
	}

}
