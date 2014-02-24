package com.example.customalarm.adapter;

import java.util.ArrayList;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.ui.SelectedAlarmItem;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SelectedAlarm extends BaseBaseAdapter<Alarm> {
	private Context mContext;

	public SelectedAlarm(Context context) {
		mContext = context;
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = new SelectedAlarmItem(mContext);
			convertView.setTag(holder);
			holder.itemView = (SelectedAlarmItem) convertView;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bundle bundle = new Bundle();
		holder.itemView.setDataBundle(bundle);
		return convertView;
	}

	public ArrayList<Alarm> getList() {
		return list;
	}

	public void setList(ArrayList<Alarm> list) {
		this.list = list;
	}

	public class ViewHolder {
		public SelectedAlarmItem itemView;
	}

}
