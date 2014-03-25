package com.example.customalarm.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;

@SuppressLint("SimpleDateFormat")
public class SetWeeklyAlarmFragment extends BaseSetAlarmFragment {
	private Button timeButton;
	private CheckBox monBox = null;
	private CheckBox tueBox = null;
	private CheckBox wedBox = null;
	private CheckBox thuBox = null;
	private CheckBox friBox = null;
	private CheckBox satBox = null;
	private CheckBox sunBox = null;

	public SetWeeklyAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_set_weekly_alarm, null);

		QCalendar = new GregorianCalendar();
		QCalendar.set(Calendar.HOUR_OF_DAY, 8);
		QCalendar.set(Calendar.MINUTE, 0);
		QCalendar.set(Calendar.MILLISECOND, 0);

		tagView = (EditText) view.findViewById(R.id.TagInput);
		timeButton = (Button) view.findViewById(R.id.timeButton);
		monBox = (CheckBox) view.findViewById(R.id.Monday);
		tueBox = (CheckBox) view.findViewById(R.id.Tuesday);
		wedBox = (CheckBox) view.findViewById(R.id.Wednesday);
		thuBox = (CheckBox) view.findViewById(R.id.Thursday);
		friBox = (CheckBox) view.findViewById(R.id.Friday);
		satBox = (CheckBox) view.findViewById(R.id.Saturday);
		sunBox = (CheckBox) view.findViewById(R.id.Sunday);

		timeButton.setOnClickListener(this);

		DateFormat df = new SimpleDateFormat("HH:mm");
		String timeString = df.format(QCalendar.getTime());
		timeButton.setText(timeString);

		ringSpinner = (Spinner) view.findViewById(R.id.ringSpinner);
		setSpinnerContent();

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.timeButton:
			new TimePickerDialog(getActivity(), new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					QCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					QCalendar.set(Calendar.MINUTE, minute);
					DateFormat df = new SimpleDateFormat("HH:mm");
					String timeString = df.format(QCalendar.getTime());
					timeButton.setText(timeString);
				}
			}, QCalendar.get(Calendar.HOUR_OF_DAY), QCalendar
					.get(Calendar.MINUTE), true).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void saveAlarm() {
		CheckBox[] boxs = { sunBox, monBox, tueBox, wedBox, thuBox, friBox,
				satBox };
		String day_of_String = "";
		for (int i = 0; i < boxs.length; i++) {
			CheckBox box = boxs[i];
			if (box.isChecked()) {
				day_of_String += (i + 1) + "_";
			}
		}
		int day_of_some[] = Alarm.String2Days(day_of_String);

		Bundle bundle = new Bundle();
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		bundle.putString(Alarm.ALARM_ID, UUID.randomUUID().toString());
		bundle.putString(Alarm.ALARM_GROUP_ID, id);
		bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_WEEKLY);
		bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
		String tmptagString = tagView.getText().toString().trim();
		if (tmptagString.equals("")) {
			tmptagString = getResources().getString(R.string.alarm_instant);
		}
		bundle.putString(Alarm.ALARM_TAG, tmptagString);
		bundle.putSerializable(Alarm.ALARM_CALENDAR, QCalendar);
		bundle.putIntArray(Alarm.ALARM_DAYS_OF_SOME, day_of_some);
		bundle.putBoolean(Alarm.ALARM_AVAILABLE, true);
		bundle.putString(Alarm.ALARM_REMARK, "");
		if (ringtones != null && ringtones.length > 0) {
			bundle.putString(Alarm.ALARM_RINGTONE,
					ringtones[ringSpinner.getSelectedItemPosition()]);
		}
		bundle.putString(Alarm.ALARM_GROUP_NAME, "");
		Alarm alarm = new Alarm(getActivity().getApplicationContext(), bundle);
		alarm.activate();
		alarm.storeInDB();
	}
}
