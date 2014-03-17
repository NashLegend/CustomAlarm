package com.example.customalarm.adapter;

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
				convertView = new MyAlarmItem(mContext, this);
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

	public void deleteItem(String id) {
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			Alarm alarm = list.get(i);
			if (alarm.getId() == id) {
				index = i;
				break;
			}
		}
		list.remove(index);
		if (index > -1) {
			for (int i = index - 1; i > -1; i--) {
				Alarm alarm = list.get(i);
				if (alarm.getIsSplitter()) {
					list.remove(i);
					break;
				}
			}
		}
		
		notifyDataSetChanged();
	}

	public class ViewHolder {
		public MyAlarmItem itemView;
		public MyAlarmSplitter splitter;
	}

	public void update() {

	}

}
