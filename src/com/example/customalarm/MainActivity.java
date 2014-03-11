package com.example.customalarm;

import com.example.customalarm.adapter.ViewPagerAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.ui.CustomViewPager;
import com.example.customalarm.ui.TabButton;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * @author NashLegend I put this as minority
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private TabButton buttonMyAlarm;
	private TabButton buttonSetAlarm;
	private CustomViewPager viewPager;
	private ViewPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Alarm.startAlarmRestore(getApplicationContext());
		initView();
	}

	private void initView() {
		
		buttonMyAlarm = (TabButton) findViewById(R.id.alarm_my);
		buttonSetAlarm = (TabButton) findViewById(R.id.alarm_set);
		viewPager = (CustomViewPager) findViewById(R.id.pager);
		adapter = new ViewPagerAdapter(getSupportFragmentManager());

		buttonMyAlarm.setOnClickListener(this);
		buttonSetAlarm.setOnClickListener(this);
		viewPager.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.alarm_my:
			viewPager.setCurrentItem(0, true);
			break;
		case R.id.alarm_set:
			viewPager.setCurrentItem(1, true);
			break;
		default:
			break;
		}
	}

}
