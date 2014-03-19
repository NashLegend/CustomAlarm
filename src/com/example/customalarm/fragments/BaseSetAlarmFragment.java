
package com.example.customalarm.fragments;

import com.example.customalarm.SetAlarmActivity;

import android.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseSetAlarmFragment extends Fragment implements OnClickListener{

    public SetAlarmActivity activity;
    public View view;

    public BaseSetAlarmFragment() {
    }

    public void setParentActivity(SetAlarmActivity activity) {
        this.activity = activity;
    }
    
    public void saveAlarm() {
        
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        
    }

}
