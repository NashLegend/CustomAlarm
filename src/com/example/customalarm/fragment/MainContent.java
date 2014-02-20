package com.example.customalarm.fragment;

import com.example.customalarm.MainActivity;
import com.example.customalarm.R;
import com.example.customalarm.R.id;
import com.example.customalarm.R.layout;
import com.example.customalarm.adapter.ViewPagerAdapter;
import com.example.customalarm.ui.CustomViewPager;
import com.example.customalarm.ui.TabButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
	private CustomViewPager viewPager;
	private ViewPagerAdapter adapter;

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
		viewPager = (CustomViewPager) view.findViewById(R.id.pager);
		adapter = new ViewPagerAdapter(getFragmentManager());

		buttonAllAlarm.setOnClickListener(this);
		buttonFestvalAlarm.setOnClickListener(this);
		buttonMyAlarm.setOnClickListener(this);
		buttonSelectAlarm.setOnClickListener(this);
		buttonSetAlarm.setOnClickListener(this);
		buttonWorkAlarm.setOnClickListener(this);
		buttonAllAlarm.setOnClickListener(this);
		viewPager.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.alarm_my:
			viewPager.setCurrentItem(0, true);
			break;
		case R.id.alarm_set:
			viewPager.setCurrentItem(1, true);
			break;
		case R.id.alarm_select:
			viewPager.setCurrentItem(2, true);
			break;
		case R.id.alarm_work:
			viewPager.setCurrentItem(3, true);
			break;
		case R.id.alarm_festival:
			viewPager.setCurrentItem(4, true);
			break;
		case R.id.alarm_all:
			((MainActivity) getActivity()).showRight();
			break;

		default:
			break;
		}
	}

}
