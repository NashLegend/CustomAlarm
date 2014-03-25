package com.example.customalarm.fragments;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.ui.DeleteAlarmDialog;
import com.example.customalarm.ui.NumberPickerDialog;

public class SetCountDownAlarmFragment extends BaseSetAlarmFragment {
	private Button hourButton;
	private Button minuteButton;
	private int hour = 3;
	private int minute = 0;

	public SetCountDownAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_set_count_down_alarm, null);
		tagView = (EditText) view.findViewById(R.id.TagInput);
		hourButton = (Button) view.findViewById(R.id.hourButton);
		minuteButton = (Button) view.findViewById(R.id.minuteButton);
		hourButton.setOnClickListener(this);
		minuteButton.setOnClickListener(this);

		hourButton.setText("3");

		minuteButton.setText("0");

		ringSpinner = (Spinner) view.findViewById(R.id.ringSpinner);
		setSpinnerContent();

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hourButton:
			NumberPickerDialog.Builder builder = new NumberPickerDialog.Builder(
					getActivity());
			NumberPickerDialog dialog1 = builder
					.setNumberSetListener(
							new NumberPickerDialog.onNumberSetListener() {

								@Override
								public void onNumberSet(DialogInterface dialog,
										int num) {
									hourButton.setText(String.valueOf(num));
									hour = num;
									dialog.dismiss();
								}
							}).setMaxValue(72).setMinValue(0).create();
			dialog1.show();
			break;
		case R.id.minuteButton:
			NumberPickerDialog.Builder builder2 = new NumberPickerDialog.Builder(
					getActivity());
			NumberPickerDialog dialog2 = builder2
					.setNumberSetListener(
							new NumberPickerDialog.onNumberSetListener() {

								@Override
								public void onNumberSet(DialogInterface dialog,
										int num) {
									minuteButton.setText(String.valueOf(num));
									minute = num;
									dialog.dismiss();
								}
							}).setMaxValue(59).setMinValue(0).create();
			dialog2.show();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	public void saveAlarm() {
		QCalendar = new GregorianCalendar();
		int minutes = hour * 60 + minute;
		if (minutes > 0) {
			QCalendar.add(Calendar.MINUTE, minutes);
			Bundle bundle = new Bundle();
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			bundle.putString(Alarm.ALARM_ID, UUID.randomUUID().toString());
			bundle.putString(Alarm.ALARM_GROUP_ID, id);
			bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_COUNT_DOWN);
			bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
			String tmptagString = tagView.getText().toString().trim();
			if (tmptagString.equals("")) {
				tmptagString = getResources().getString(
						R.string.alarm_count_down);
			}
			bundle.putString(Alarm.ALARM_TAG, tmptagString);
			bundle.putSerializable(Alarm.ALARM_CALENDAR, QCalendar);
			bundle.putIntArray(Alarm.ALARM_DAYS_OF_SOME, null);
			bundle.putBoolean(Alarm.ALARM_AVAILABLE, true);
			bundle.putString(Alarm.ALARM_REMARK, "");
			bundle.putString(Alarm.ALARM_GROUP_NAME, "");
			if (ringtones != null && ringtones.length > 0) {
				bundle.putString(Alarm.ALARM_RINGTONE,
						ringtones[ringSpinner.getSelectedItemPosition()]);
			}
			Alarm alarm = new Alarm(getActivity().getApplicationContext(),
					bundle);
			alarm.activate();
			alarm.storeInDB();
		} else {
			// Too nearly
		}

	}
}
