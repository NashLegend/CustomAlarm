package com.example.customalarm.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customalarm.AlarmDetail;
import com.example.customalarm.R;
import com.example.customalarm.adapter.MyAlarmAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.interfase.BaseItem;

/**
 * @author NashLegend
 * 
 */
public class MyAlarmItem extends RelativeLayout implements BaseItem, OnTouchListener {
	private TextView tagTextView;
	private TextView descTextView;
	private Alarm alarm;
	private Bundle bundle;
	private GestureDetector detector;
	private Context context;
	private MyAlarmAdapter adapter;

	public MyAlarmItem(Context context, MyAlarmAdapter adapter) {
		super(context);
		this.context = context;
		this.adapter = adapter;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_my_alarm, this);
		tagTextView = (TextView) findViewById(R.id.alarmtag);
		descTextView = (TextView) findViewById(R.id.alarmtime);
		setLongClickable(true);
		setOnTouchListener(this);
		detector = new GestureDetector(context, new MyAlarmGestureListener(context));
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO 自动生成的方法存根
		return detector.onTouchEvent(event);
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
		this.alarm = new Alarm(context.getApplicationContext(), bundle);
		tagTextView.setText(alarm.getTag());
		descTextView.setText(alarm.getAlarmDescription());
	}

	public void gotoDetailActivity() {
		Intent intent = new Intent(context, AlarmDetail.class);
		intent.putExtra("alarm", MyAlarmItem.this.bundle);
		context.startActivity(intent);
	}

	public void deleteAlarm() {
		DeleteAlarmDialog.Builder builder = new DeleteAlarmDialog.Builder(context);
		DeleteAlarmDialog dialog = builder.setPositiveButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				adapter.deleteItem(alarm.getId());
				alarm.delete();
				Log.i("cus", "33333");
				dialog.dismiss();
			}
		}).create();
		dialog.show();
	}

	public class MyAlarmGestureListener extends SimpleOnGestureListener {
		private Context context;

		public MyAlarmGestureListener(Context context) {
			this.context = context;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Toast.makeText(context, "LongClick", Toast.LENGTH_SHORT).show();
			deleteAlarm();
			super.onLongPress(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			gotoDetailActivity();
			return super.onSingleTapConfirmed(e);
		}
	}

}
