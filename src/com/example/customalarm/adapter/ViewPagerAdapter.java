package com.example.customalarm.adapter;

import java.util.ArrayList;

import com.example.customalarm.fragment.MyAlarmFragment;
import com.example.customalarm.fragment.SetAlarmFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;

	public ViewPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
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
