package com.example.customalarm.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.ui.NumberPickerDialog;

@SuppressLint("SimpleDateFormat")
public class SetMonthlyAlarmFragment extends BaseSetAlarmFragment {
	private Button dayButton;
	private Button timeButton;

	public SetMonthlyAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_set_monthly_alarm, null);
		tagView = (EditText) view.findViewById(R.id.TagInput);
		dayButton = (Button) view.findViewById(R.id.daysofmonth);
		timeButton = (Button) view.findViewById(R.id.timeButton);
		ringSpinner = (Spinner) view.findViewById(R.id.ringSpinner);

		dayButton.setOnClickListener(this);
		timeButton.setOnClickListener(this);

		if (setMode == BaseSetAlarmFragment.MODE_ADD_ALARM) {

			QCalendar = new GregorianCalendar();
			QCalendar.set(Calendar.MONTH, Calendar.JANUARY);// 设为一月则可接收所有日期
			QCalendar.set(Calendar.DAY_OF_MONTH, 1);
			QCalendar.set(Calendar.HOUR_OF_DAY, 8);
			QCalendar.set(Calendar.MINUTE, 0);
			QCalendar.set(Calendar.MILLISECOND, 0);

		} else {
			tagView.setText(alarm.getTag());
			QCalendar=alarm.getAudreyCalendar();
		}
		
		dayButton.setText(QCalendar.get(Calendar.DAY_OF_MONTH) + "");
		DateFormat df = new SimpleDateFormat("HH:mm");
		String timeString = df.format(QCalendar.getTime());
		timeButton.setText(timeString);
		setSpinnerContent();

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.daysofmonth:
			NumberPickerDialog.Builder builder = new NumberPickerDialog.Builder(
					getActivity());
			NumberPickerDialog dialog1 = builder
					.setNumberSetListener(
							new NumberPickerDialog.onNumberSetListener() {

								@Override
								public void onNumberSet(DialogInterface dialog,
										int num) {
									dayButton.setText(String.valueOf(num));
									QCalendar.set(Calendar.DAY_OF_MONTH, num);
									dialog.dismiss();
								}
							}).setMaxValue(72).setMinValue(0).create();
			dialog1.show();
			break;
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
		super.onClick(v);
	}

	@Override
	public void saveAlarm() {
		Bundle bundle = new Bundle();
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		bundle.putString(Alarm.ALARM_ID, id);
		bundle.putString(Alarm.ALARM_GROUP_ID, id);
		bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_MONTHLY);
		bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
		String tmptagString = tagView.getText().toString().trim();
		if (tmptagString.equals("")) {
			tmptagString = getResources().getString(R.string.alarm_monthly);
		}
		bundle.putString(Alarm.ALARM_TAG, tmptagString);
		bundle.putSerializable(Alarm.ALARM_CALENDAR, QCalendar);
		bundle.putIntArray(Alarm.ALARM_DAYS_OF_SOME, null);
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

	@Override
	public void editAlarm() {
		String tmptagString = tagView.getText().toString().trim();
		if (tmptagString.equals("")) {
			tmptagString = getResources().getString(R.string.alarm_daily);
		}
		alarm.setAudreyCalendar(QCalendar);
		alarm.setTag(tmptagString);
		alarm.edit();
		alarm.activate();
	}

}
