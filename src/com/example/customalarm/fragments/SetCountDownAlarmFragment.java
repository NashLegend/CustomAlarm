package com.example.customalarm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customalarm.R;

public class SetCountDownAlarmFragment extends BaseSetAlarmFragment {

    public SetCountDownAlarmFragment() {
        // TODO 自动生成的构造函数存根
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_set_count_down_alarm, null);
        return view;
    }
}
