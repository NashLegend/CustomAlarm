package com.example.customalarm.adapter;

import java.util.ArrayList;

import com.example.customalarm.model.AlarmType;
import com.example.customalarm.ui.AlarmTypeItem;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AlarmTypeAdapter extends BaseBaseAdapter<AlarmType> {

	private Context mContext;

	public AlarmTypeAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = new AlarmTypeItem(mContext);
			convertView.setTag(holder);
			holder.itemView = (AlarmTypeItem) convertView;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bundle bundle = new Bundle();
		holder.itemView.setDataBundle(bundle);
		return convertView;
	}

	public class ViewHolder {
		public AlarmTypeItem itemView;
	}

}
