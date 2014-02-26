package com.example.customalarm.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BaseBaseAdapter<T> extends BaseAdapter {

	public ArrayList<T> list = new ArrayList<T>();

	public boolean add(T object) {
		return list.add(object);
	}

	public void add(int index, T object) {
		list.add(index, object);
	}

	public void addAll(ArrayList<T> arrayList) {
		list.addAll(arrayList);
	}

	public void addAll(int index, ArrayList<T> arrayList) {
		list.addAll(index, arrayList);
	}

	public BaseBaseAdapter() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		return null;
	}

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

}
