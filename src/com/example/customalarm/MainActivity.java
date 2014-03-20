package com.example.customalarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.customalarm.adapter.MyAlarmAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.db.AlarmHelper;
import com.example.customalarm.ui.MyAlarmSplitter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * @author NashLegend I put this as minority
 */
public class MainActivity extends Activity {
	private ActionBar mActionBar;
	private MyAlarmAdapter adapter;
	private ListView listView;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Alarm.startAlarmRestore(getApplicationContext());
		initView();
	}

	private void initView() {
		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(true);
		imageView = (ImageView) findViewById(R.id.my_clarm_bg_img);
		listView = (ListView) findViewById(R.id.MyAlarmList);
		adapter = new MyAlarmAdapter(this);
		listView.setAdapter(adapter);
		buildAlarmList();
	}

	public void buildAlarmList() {
		Alarm todayAlarm = new Alarm(this, null);
		todayAlarm.setIsSplitter(true);
		todayAlarm.setTag("Today");
		todayAlarm.setRemark("weather");

		ArrayList<Alarm> alarms = getAllAlarms(this);
		
		if (alarms.size() == 0) {
			imageView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		} else {
			imageView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			AlarmComparator alarmComparator = new AlarmComparator();
			Collections.sort(alarms, alarmComparator);
			alarms.add(0, todayAlarm);

			int tmpNum = 0;
			int currentSplitterType = alarms.get(1).getSpanType();
			Alarm currentSplitterAlarm = todayAlarm;
			Alarm alarm2 = null;
			for (int i = 1; i < alarms.size(); i++) {
				alarm2 = alarms.get(i);
				
				if (alarm2.getSpanType() == currentSplitterType) {
					tmpNum++;
				} else {
					if (tmpNum == 0) {
						alarms.remove(currentSplitterAlarm);
						i--;
					} else {
						currentSplitterAlarm.setType(currentSplitterType);
						currentSplitterAlarm.setTag(MyAlarmSplitter
								.type2Date(currentSplitterType));
						currentSplitterAlarm.setNumInSplitter(tmpNum);
						currentSplitterAlarm.setRemark(":" + tmpNum + "个");
					}

					tmpNum = 1;
					Alarm alarm3 = new Alarm(this, null);
					alarm3.setIsSplitter(true);
					currentSplitterAlarm = alarm3;
					alarms.add(i, currentSplitterAlarm);
					currentSplitterType = alarm2.getSpanType();
					i++;
				}
			}

			if (tmpNum == 0) {
				alarms.remove(currentSplitterAlarm);
			} else {
				currentSplitterAlarm.setType(currentSplitterType);
				currentSplitterAlarm.setTag(MyAlarmSplitter
						.type2Date(currentSplitterType));
				currentSplitterAlarm.setNumInSplitter(tmpNum);
				currentSplitterAlarm.setRemark(":" + tmpNum + "个");
			}
			adapter.setList(alarms);
			adapter.notifyDataSetChanged();
		}
	}

	private ArrayList<Alarm> getAllAlarms(Context context) {
		AlarmHelper helper = new AlarmHelper(context);
		return helper.getAlarms();
	}

	public class AlarmComparator implements Comparator<Alarm> {

		@Override
		public int compare(Alarm lhs, Alarm rhs) {
			Long com = lhs.getNextRingSpan() - rhs.getNextRingSpan();
			if (com > 0) {
				return 1;
			} else if (com == 0) {
				return 0;
			} else {
				return -1;
			}

		}
	}

	private void slog(String str) {
		Log.i("cus", str + "__");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自动生成的方法存根
		switch (item.getItemId()) {
		case R.id.alarm_set:
			Intent intent = new Intent(this, SetAlarmActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
