
package com.example.customalarm.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.customalarm.SetAlarmActivity;
import com.example.customalarm.core.Alarm;

import android.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BaseSetAlarmFragment extends Fragment implements OnClickListener {

    public SetAlarmActivity activity;
    public View view;
    public GregorianCalendar QCalendar;
    public EditText tagView;
    public Spinner ringSpinner;
    public String[] ringtones;
    public Uri[] uris;
    private ArrayAdapter<String> spinnerAdapter;
    public int setMode = MODE_ADD_ALARM;// 默认为添加闹钟
    public Alarm alarm;

    public static final int MODE_ADD_ALARM = 0;
    public static final int MODE_EDIT_ALARM = 1;

    public BaseSetAlarmFragment() {
    }

    public void setParentActivity(SetAlarmActivity activity) {
        this.activity = activity;
    }

    public void saveAlarm() {

    }

    public void editAlarm() {

    }

    public GregorianCalendar getCalendarAfter30Mins() {
        GregorianCalendar calendar = new GregorianCalendar();
        if (calendar.get(Calendar.MINUTE) >= 30) {
            calendar.add(Calendar.MINUTE, 60 - calendar.get(Calendar.MINUTE));
        } else {
            calendar.add(Calendar.MINUTE, 30 - calendar.get(Calendar.MINUTE));
        }

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

//        manager.getRingtone是个耗时操作，故不采用
    public void setSpinnerContent() {
        // TODO
        RingtoneManager manager = new RingtoneManager(getActivity());
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();
        int num = cursor.getCount();
        if (num > 0) {
            ringtones = new String[num];
            uris = new Uri[num];
            int idx = 0;
            if (setMode == MODE_ADD_ALARM) {
                if (cursor.moveToFirst()) {
                    do {
                        // TODO
                        ringtones[cursor.getPosition()] = cursor
                                .getString(RingtoneManager.TITLE_COLUMN_INDEX);
                    } while (cursor.moveToNext());
                }
            } else {
                // TODO
                if (cursor.moveToFirst()) {
                    do {
                        // TODO
                        int i=cursor.getPosition();
                        ringtones[i] = cursor
                                .getString(RingtoneManager.TITLE_COLUMN_INDEX);
                        if (alarm.getRingtoneId().equals(ringtones[i])) {
                            idx = i;
                        }
                    } while (cursor.moveToNext());
                }
            }
            spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, ringtones);
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ringSpinner.setAdapter(spinnerAdapter);
            ringSpinner.setSelection(idx);

        } else {
            Toast.makeText(getActivity(), "No Ringtones Found !",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根

    }

    public int getSetMode() {
        return setMode;
    }

    public void setSetMode(int setMode) {
        this.setMode = setMode;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

}
