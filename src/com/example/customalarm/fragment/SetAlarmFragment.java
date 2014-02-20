package com.example.customalarm.fragment;

import com.example.customalarm.R;
import com.example.customalarm.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SetAlarmFragment extends Fragment {
	private View view;

	public SetAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_set_alarm, null);
		return view;
	}
}
