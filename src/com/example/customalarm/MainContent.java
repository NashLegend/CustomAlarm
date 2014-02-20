package com.example.customalarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainContent extends Fragment implements OnClickListener {
	private TabButton buttonMyAlarm;
	private TabButton buttonSetAlarm;
	private TabButton buttonSelectAlarm;
	private TabButton buttonWorkAlarm;
	private TabButton buttonFestvalAlarm;
	private TabButton buttonAllAlarm;

	public MainContent() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_main, null);
		buttonAllAlarm = (TabButton) view.findViewById(R.id.alarm_all);
		buttonFestvalAlarm = (TabButton) view.findViewById(R.id.alarm_festival);
		buttonMyAlarm = (TabButton) view.findViewById(R.id.alarm_my);
		buttonSelectAlarm = (TabButton) view.findViewById(R.id.alarm_select);
		buttonSetAlarm = (TabButton) view.findViewById(R.id.alarm_set);
		buttonWorkAlarm = (TabButton) view.findViewById(R.id.alarm_work);

		buttonAllAlarm.setOnClickListener(this);
		buttonFestvalAlarm.setOnClickListener(this);
		buttonMyAlarm.setOnClickListener(this);
		buttonSelectAlarm.setOnClickListener(this);
		buttonSetAlarm.setOnClickListener(this);
		buttonWorkAlarm.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Toast.makeText(getActivity(), "aaaaa", Toast.LENGTH_SHORT).show();
	}

}
