package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.interfase.BaseItem;
import com.example.customalarm.model.AlarmType;

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
 * @author NashLegend
 * 
 */
public class AlarmTypeItem extends RelativeLayout implements BaseItem {

	private AlarmType type;
	private TextView titleTextView;
	private ImageView typeImageView;
	private Button button;

	public AlarmTypeItem(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_alarm_type, this);
		titleTextView = (TextView) findViewById(R.id.alarmtag);
		typeImageView = (ImageView) findViewById(R.id.alarmimage);
		button=(Button)findViewById(R.id.collectAlarm);
	}

	public AlarmTypeItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlarmTypeItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void setDataBundle(Bundle bundle) {
		type = (AlarmType) bundle.getSerializable("type");
		titleTextView.setText(type.getTitle());
		typeImageView.setImageResource(type.getImgId());
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 收藏，显示在主页，等会再说
				
			}
		});
	}

}
