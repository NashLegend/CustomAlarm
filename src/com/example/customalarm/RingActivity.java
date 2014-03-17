package com.example.customalarm;

import com.example.customalarm.core.Alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RingActivity extends Activity {

	private Alarm alarm;
	private Intent alarmIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alarmIntent = getIntent();
	}

}
