
package com.example.customalarm.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.customalarm.R;
import com.example.customalarm.core.Alarm;

@SuppressLint("SimpleDateFormat")
public class SetDailyAlarmFragment extends BaseSetAlarmFragment {
    private Button timeButton;

    public SetDailyAlarmFragment() {
        // TODO 自动生成的构造函数存根
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set_daily_alarm, null);
        
        QCalendar = new GregorianCalendar();
		QCalendar.set(Calendar.HOUR_OF_DAY, 8);
		QCalendar.set(Calendar.MINUTE, 0);
		QCalendar.set(Calendar.MILLISECOND, 0);
		
        tagView = (EditText) view.findViewById(R.id.TagInput);
        timeButton = (Button) view.findViewById(R.id.timeButton);
        timeButton.setOnClickListener(this);

        DateFormat df = new SimpleDateFormat("HH:mm");
        String timeString = df.format(QCalendar.getTime());
        timeButton.setText(timeString);
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
        super.onClick(v);
    }

    @Override
    public void saveAlarm() {
        Bundle bundle = new Bundle();
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        bundle.putString(Alarm.ALARM_ID, UUID.randomUUID().toString());
        bundle.putString(Alarm.ALARM_GROUP_ID, id);
        bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_DAILY);
        bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
        String tmptagString = tagView.getText().toString().trim();
        if (tmptagString.equals("")) {
            tmptagString = getResources().getString(R.string.alarm_daily);
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
    }

}
