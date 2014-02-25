package com.example.customalarm.adapter;

import java.util.ArrayList;

import com.example.customalarm.ui.GridPanel;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class GridAdapter extends PagerAdapter {

	private ArrayList<GridPanel> list = new ArrayList<GridPanel>();

	public GridAdapter() {
	}

	public void setData(ArrayList<GridPanel> panels) {
		list = panels;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list.get(position));
		return list.get(position);
	}

}
