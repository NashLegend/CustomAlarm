package com.example.customalarm.adapter;

import java.util.ArrayList;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.ui.MyAlarmItem;
import com.example.customalarm.ui.MyAlarmSplitter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author NashLegend
 * 
 */
public class MyAlarmAdapter extends BaseBaseAdapter<Alarm> {
	private Context mContext;

	public MyAlarmAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return list.get(position).getIsSplitter() ? 1 : 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO 自动生成的方法存根
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			if (getItemViewType(position) == 1) {
				convertView = new MyAlarmSplitter(mContext);
				holder.splitter = (MyAlarmSplitter) convertView;
			} else {
				convertView = new MyAlarmItem(mContext);
				holder.itemView = (MyAlarmItem) convertView;
			}
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (getItemViewType(position) == 1) {
			holder.splitter.setDate(list.get(position).getTag());
			holder.splitter.setDesc(list.get(position).getRemark());
		} else {
			Bundle bundle = Alarm.alarm2Bundle(list.get(position));
			holder.itemView.setDataBundle(bundle);
		}
		return convertView;
	}

	public ArrayList<Alarm> getList() {
		return list;
	}

	public void setList(ArrayList<Alarm> list) {
		this.list = list;
	}

	public class ViewHolder {
		public MyAlarmItem itemView;
		public MyAlarmSplitter splitter;
	}

	public void update() {

	}

}
