
package com.example.customalarm.fragments;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.customalarm.SetAlarmActivity;

import android.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class BaseSetAlarmFragment extends Fragment implements OnClickListener{

    public SetAlarmActivity activity;
    public View view;
    public GregorianCalendar QCalendar;
    public EditText tagView;
    public BaseSetAlarmFragment() {
    }

    public void setParentActivity(SetAlarmActivity activity) {
        this.activity = activity;
    }
    
    public void saveAlarm() {
        
    }
    
    public GregorianCalendar getCalendarAfter30Mins() {
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
        // TODO 自动生成的方法存根
        
    }

}
