package com.example.customalarm;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.db.AlarmHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RingActivity extends Activity {

	private Alarm alarm;
	private Intent alarmIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ring);
		alarmIntent = getIntent();
		AlarmHelper helper = new AlarmHelper(this);
		alarm = helper.getAlarmByID(alarmIntent.getStringExtra(Alarm.ALARM_ID));
		if (alarm != null) {
			
		} else {
			// alarm is null
		}
	}

	public void shutDown() {
		if (alarm != null) {
			alarm.shutDown();
		}
	}

}
