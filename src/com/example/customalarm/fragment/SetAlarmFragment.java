package com.example.customalarm.fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class SetAlarmFragment extends Fragment implements OnClickListener {
	private View view;
	private EditText tagView;
	private Button dateButton;
	private Button timeButton;
	private Button confirmButton;
	private GregorianCalendar QCalendar;

	public SetAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		QCalendar = getCalendarAfter30Mins();
		view = inflater.inflate(R.layout.fragment_set_alarm, null);
		tagView = (EditText) view.findViewById(R.id.TagInput);
		dateButton = (Button) view.findViewById(R.id.dateButton);
		timeButton = (Button) view.findViewById(R.id.timeButton);
		confirmButton = (Button) view.findViewById(R.id.saveAlarm);

		dateButton.setText(QCalendar.get(Calendar.YEAR) + "-"
				+ QCalendar.get(Calendar.MONTH) + "-"
				+ QCalendar.get(Calendar.DAY_OF_MONTH));

		timeButton.setText(QCalendar.get(Calendar.HOUR_OF_DAY) + "-"
				+ QCalendar.get(Calendar.MINUTE));

		dateButton.setOnClickListener(this);
		timeButton.setOnClickListener(this);
		confirmButton.setOnClickListener(this);

		return view;
	}

	private GregorianCalendar getCalendarAfter30Mins() {
		GregorianCalendar calendar = new GregorianCalendar();
		if (calendar.get(Calendar.MINUTE) >= 30) {
			calendar.add(Calendar.MINUTE, 60 - calendar.get(Calendar.MINUTE));
		} else {
			calendar.add(Calendar.MINUTE, 30 - calendar.get(Calendar.MINUTE));
		}

		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dateButton:
			new DatePickerDialog(getActivity(), new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					QCalendar.set(year, monthOfYear, dayOfMonth);
					dateButton.setText(year + "/" + (monthOfYear + 1) + "/"
							+ dayOfMonth);
				}
			}, QCalendar.get(Calendar.YEAR), QCalendar.get(Calendar.MONTH),
					QCalendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.timeButton:
			new TimePickerDialog(getActivity(), new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO 自动生成的方法存根
					QCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					QCalendar.set(Calendar.MINUTE, minute);
					timeButton.setText(hourOfDay + ":" + minute);
				}
			}, QCalendar.get(Calendar.HOUR_OF_DAY), QCalendar
					.get(Calendar.MINUTE), true).show();
			break;
		case R.id.saveAlarm:
			Bundle bundle = new Bundle();

			String id = UUID.randomUUID().toString().replaceAll("-", "");
			bundle.putString(Alarm.ALARM_ID, UUID.randomUUID().toString());
			bundle.putString(Alarm.ALARM_GROUP_ID, id);
			bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_ONE_TIME);
			bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
			String tmptagString = tagView.getText().toString().trim();
			if (tmptagString.equals("")) {
				tmptagString = "快速闹钟";
			}
			bundle.putString(Alarm.ALARM_TAG, tmptagString);
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
