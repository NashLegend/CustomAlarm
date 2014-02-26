package com.example.customalarm.fragment;

import com.example.customalarm.R;
import com.example.customalarm.adapter.SelectedAlarmAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SelectedAlarmFragment extends Fragment {
	private View view;
	private ListView listView;
	private SelectedAlarmAdapter adapter;

	public SelectedAlarmFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_selected_alarm, null);
		listView = (ListView) view.findViewById(R.id.SelectedAlarmList);
		adapter = new SelectedAlarmAdapter(getActivity());
		listView.setAdapter(adapter);
		buildAlarmList();
		return view;
	}

	private void buildAlarmList() {
		
	}

}
