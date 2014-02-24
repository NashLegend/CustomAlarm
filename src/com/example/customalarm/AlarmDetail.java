package com.example.customalarm;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * @author NashLegend
 */
public class AlarmDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_detail, menu);
		return true;
	}

}
