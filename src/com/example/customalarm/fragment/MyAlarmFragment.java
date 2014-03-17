package com.example.customalarm.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.customalarm.R;
import com.example.customalarm.R.layout;
import com.example.customalarm.adapter.MyAlarmAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.db.AlarmHelper;
import com.example.customalarm.ui.MyAlarmSplitter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MyAlarmFragment extends Fragment {
	private View view;
	private ListView listView;
	private MyAlarmAdapter adapter;
	private View leftView;
	private ImageView imageView;

	public MyAlarmFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_alarm, null);
		listView = (ListView) view.findViewById(R.id.MyAlarmList);
		imageView = (ImageView) view.findViewById(R.id.my_clarm_bg_img);
		leftView = view.findViewById(R.id.my_alarm_left);
		adapter = new MyAlarmAdapter(getActivity());
		listView.setAdapter(adapter);

		buildAlarmList();
		return view;
	}

	public void buildAlarmList() {
		Alarm todayAlarm = new Alarm(getActivity(), null);
		todayAlarm.setIsSplitter(true);
		todayAlarm.setTag("Today");
		todayAlarm.setRemark("weather");

		ArrayList<Alarm> alarms = getAllAlarms(getActivity());
		if (alarms.size() == 0) {
			imageView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			leftView.setVisibility(View.GONE);
		} else {
			imageView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			leftView.setVisibility(View.VISIBLE);
			AlarmComparator alarmComparator = new AlarmComparator();
			Collections.sort(alarms, alarmComparator);

			alarms.add(0, todayAlarm);

			int tmpNum = 0;
			int currentSplitterType = MyAlarmSplitter.TODAY;
			Alarm currentSplitterAlarm = todayAlarm;
			Alarm alarm2 = null;
			for (int i = 1; i < alarms.size(); i++) {
				alarm2 = alarms.get(i);
				if (alarm2.getSpanType() > 0) {
					if (alarm2.getSpanType() == currentSplitterType) {
						tmpNum++;
					} else {
						currentSplitterAlarm.setTag(MyAlarmSplitter.type2Date(currentSplitterType));
						currentSplitterAlarm.setRemark(":" + tmpNum + "个");
						Alarm alarm3 = new Alarm(getActivity(), null);
						alarm3.setIsSplitter(true);
						currentSplitterAlarm = alarm3;
						alarms.add(i, currentSplitterAlarm);
						currentSplitterType = alarm2.getSpanType();
						i++;
					}
				} else {
					if (i < (alarms.size() - 1)) {
						currentSplitterType = alarms.get(i + 1).getSpanType();
					}
				}
			}

			if (tmpNum == 0) {
				alarms.remove(currentSplitterAlarm);
			} else {
				currentSplitterAlarm.setTag(MyAlarmSplitter.type2Date(alarm2.getSpanType()));
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
}
