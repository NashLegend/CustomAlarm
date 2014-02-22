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

/**
 * @author Pan
 * 
 */
public class RecommendedAlarmItem extends RelativeLayout implements BaseItem {
	private TextView remarkTextView;
	private TextView tagTextView;
	private TextView descTextView;
	private ImageView imageView;
	private Button button;
	private Alarm alarm;

	public RecommendedAlarmItem(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_recommended_alarm, this);
		remarkTextView = (TextView) findViewById(R.id.alarmremark);
		tagTextView = (TextView) findViewById(R.id.alarmtag);
		descTextView = (TextView) findViewById(R.id.alarmtime);
		imageView=(ImageView)findViewById(R.id.alarmimage);//
		//TODO 如果已经添加则显示为对号，以后再说
		button = (Button) findViewById(R.id.addAlarm);
	}

	public RecommendedAlarmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RecommendedAlarmItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setDataBundle(Bundle bundle) {
		alarm = new Alarm(getContext().getApplicationContext(), bundle);
		remarkTextView.setText(alarm.getRemark());
		tagTextView.setText(alarm.getTag());
		descTextView.setText(alarm.getAlarmDescription());
		//TODO 
//		imageView.setImageResource(alarm.getImgId());
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
