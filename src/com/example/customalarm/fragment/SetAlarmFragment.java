package com.example.customalarm.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.example.customalarm.R;
import com.example.customalarm.adapter.GridAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.model.GridData;
import com.example.customalarm.ui.GridPanel;
import com.example.customalarm.util.DisplayTools;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.LinearLayout.LayoutParams;

public class SetAlarmFragment extends Fragment implements OnClickListener {
	private View view;
	private TextView tagView;
	private Button dateButton;
	private Button timeButton;
	private Button confirmButton;
	private ViewPager pager;
	private LinearLayout indicatorLayout;
	private GridAdapter adapter;
	private GregorianCalendar QCalendar;

	public SetAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		QCalendar = getCalendarAfter30Mins();
		view = inflater.inflate(R.layout.fragment_set_alarm, null);
		tagView = (TextView) view.findViewById(R.id.TagInput);
		dateButton = (Button) view.findViewById(R.id.dateButton);
		timeButton = (Button) view.findViewById(R.id.timeButton);
		confirmButton = (Button) view.findViewById(R.id.saveAlarm);
		pager = (ViewPager) view.findViewById(R.id.pager);
		indicatorLayout = (LinearLayout) view.findViewById(R.id.indicator);

		dateButton.setText(QCalendar.get(Calendar.YEAR) + "-"
				+ QCalendar.get(Calendar.MONTH) + "-"
				+ QCalendar.get(Calendar.DAY_OF_MONTH));

		timeButton.setText(QCalendar.get(Calendar.HOUR_OF_DAY) + "-"
				+ QCalendar.get(Calendar.MINUTE));

		dateButton.setOnClickListener(this);
		timeButton.setOnClickListener(this);
		confirmButton.setOnClickListener(this);

		setPager(getDatas());

		setPagerListener();
		return view;
	}

	private GregorianCalendar getCalendarAfter30Mins() {
		GregorianCalendar calendar = new GregorianCalendar();
		if (calendar.get(Calendar.MINUTE) >= 30) {
			calendar.add(Calendar.MINUTE, 60 - calendar.get(Calendar.MINUTE));
		} else {
			calendar.add(Calendar.MINUTE, 30 - calendar.get(Calendar.MINUTE));
		}

		return calendar;
	}

	private ArrayList<GridData> getDatas() {
		return null;
	}

	public void setPager(ArrayList<GridData> datas) {
		if (datas != null) {
			int dim = (int) (getResources().getDimension(R.dimen.grid_item_dim));
			int wid = DisplayTools.getScreenWidth(getActivity());
			int hei = DisplayTools.getWindowHeight((Activity) getActivity());
			int col = (int) (wid / dim);
			int lines = (int) Math.ceil(hei / dim);
			int tot = col * lines;
			int numPanel = (int) Math.ceil((float) (datas.size()) / tot);
			ArrayList<GridPanel> panels = new ArrayList<GridPanel>();
			for (int i = 0; i < numPanel; i++) {
				int n = tot;
				if (i == numPanel) {
					n = datas.size() - i * tot;
				}
				ArrayList<GridData> tmpDatas = new ArrayList<GridData>();
				for (int j = 0; j < n; j++) {
					tmpDatas.add(datas.get(i * tot + j));
				}
				GridPanel panel = new GridPanel(getActivity());
				LayoutParams params = new LayoutParams(-1, -1);
				panel.setLayoutParams(params);
				panel.setData(tmpDatas);
				panels.add(panel);
				addIndicator();
			}
			adapter = new GridAdapter();
			adapter.setData(panels);
			pager.setAdapter(adapter);
			setIndicatorIndex(0);
		}

	}

	private void addIndicator() {
		// TODO
		indicatorLayout.getWidth();
	}

	private void setIndicatorIndex(int index) {
		// TODO
	}

	private void setPagerListener() {
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setIndicatorIndex(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		pager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		Calendar calendar = Calendar.getInstance();
		switch (v.getId()) {
		case R.id.dateButton:
			new DatePickerDialog(getActivity(), new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					QCalendar.set(year, monthOfYear, dayOfMonth);
					dateButton.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.timeButton:
			new TimePickerDialog(getActivity(), new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO 自动生成的方法存根
					QCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					QCalendar.set(Calendar.MINUTE, minute);
					timeButton.setText(hourOfDay + "-" + (minute + 1));
				}
			}, calendar.get(Calendar.HOUR_OF_DAY), calendar
					.get(Calendar.MINUTE), true).show();
			break;
		case R.id.saveAlarm:
			Bundle bundle = new Bundle();

			String id = UUID.randomUUID().toString();
			bundle.putString(Alarm.ALARM_ID, UUID.randomUUID().toString());
			bundle.putString(Alarm.ALARM_GROUP_ID, id);
			bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_ONE_TIME);
			bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
			bundle.putString(Alarm.ALARM_TAG, tagView.getText().toString());
			bundle.putSerializable(Alarm.ALARM_CALENDAR, QCalendar);
			bundle.putIntArray(Alarm.ALARM_DAYS_OF_SOME, null);
			bundle.putBoolean(Alarm.ALARM_AVAILABLE, true);
			bundle.putString(Alarm.ALARM_REMARK, "");
			bundle.putString(Alarm.ALARM_IMAGE, null);
			bundle.putString(Alarm.ALARM_GROUP_NAME, "");
			Alarm alarm = new Alarm(getActivity().getApplicationContext(),
					bundle);
			alarm.activate();
			alarm.storeInDB();
			// TODO 放到我的闹钟列表中
			break;

		default:
			break;
		}
	}
}
