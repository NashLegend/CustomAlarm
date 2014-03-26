package com.example.customalarm;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.fragments.BaseSetAlarmFragment;
import com.example.customalarm.fragments.SetCountDownAlarmFragment;
import com.example.customalarm.fragments.SetDailyAlarmFragment;
import com.example.customalarm.fragments.SetInstantAlarmFragment;
import com.example.customalarm.fragments.SetMonthlyAlarmFragment;
import com.example.customalarm.fragments.SetWeeklyAlarmFragment;
import com.example.customalarm.fragments.SetYearlyAlarmFragment;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author NashLegend
 */
public class AlarmDetail extends Activity {

	private Alarm alarm;
	private BaseSetAlarmFragment currentFragment;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_detail);
		alarm = new Alarm(getApplicationContext(), getIntent().getBundleExtra(
				"alarm"));

		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);

		loadFragment();
	}

	public void loadFragment() {
		BaseSetAlarmFragment fragment = null;
		switch (alarm.getType()) {
		case Alarm.ALARM_DAILY:
			fragment = (new SetDailyAlarmFragment());
			break;
		case Alarm.ALARM_WEEKLY:
			fragment = (new SetWeeklyAlarmFragment());
			break;
		case Alarm.ALARM_MONTHLY:
			fragment = (new SetMonthlyAlarmFragment());
			break;
		case Alarm.ALARM_YEARLY:
			fragment = (new SetYearlyAlarmFragment());
			break;
		case Alarm.ALARM_ONE_TIME:
			fragment = (new SetInstantAlarmFragment());
			break;
		case Alarm.ALARM_COUNT_DOWN:
			fragment = (new SetCountDownAlarmFragment());

		default:
			break;
		}

		fragment.setSetMode(BaseSetAlarmFragment.MODE_EDIT_ALARM);
		fragment.setAlarm(alarm);

		replaceFragment(fragment);
	}

	public void replaceFragment(BaseSetAlarmFragment fragment) {
		currentFragment = fragment;
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.alarm_set_window, currentFragment);
		transaction.commit();
	}

	public void done() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.save:
			currentFragment.editAlarm();
			done();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_detail, menu);
		return true;
	}
}
