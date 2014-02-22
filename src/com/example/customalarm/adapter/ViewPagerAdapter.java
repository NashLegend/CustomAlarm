package com.example.customalarm.adapter;

import java.util.ArrayList;

import com.example.customalarm.fragment.MyAlarmFragment;
import com.example.customalarm.fragment.SelectedAlarmFragment;
import com.example.customalarm.fragment.SetAlarmFragment;
import com.example.customalarm.fragment.RecommendedAlarmFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		list = new ArrayList<Fragment>();
		list.add(new MyAlarmFragment());
		list.add(new SetAlarmFragment());
		list.add(new SelectedAlarmFragment());
		list.add(new RecommendedAlarmFragment());
	}

	@Override
	public Fragment getItem(int index) {
		return list.get(index);
	}

	@Override
	public int getCount() {
		return list.size();
	}
}
