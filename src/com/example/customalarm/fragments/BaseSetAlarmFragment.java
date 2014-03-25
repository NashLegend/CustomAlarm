package com.example.customalarm.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.customalarm.SetAlarmActivity;

import android.R;
import android.app.Fragment;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BaseSetAlarmFragment extends Fragment implements OnClickListener {

	public SetAlarmActivity activity;
	public View view;
	public GregorianCalendar QCalendar;
	public EditText tagView;
	public Spinner ringSpinner;
	public ArrayList<Ringtone> list = new ArrayList<Ringtone>();
	public String[] ringtones;
	private ArrayAdapter<String> spinnerAdapter;

	public BaseSetAlarmFragment() {
	}

	public void setParentActivity(SetAlarmActivity activity) {
		this.activity = activity;
	}

	public void saveAlarm() {

	}

	public GregorianCalendar getCalendarAfter30Mins() {
		GregorianCalendar calendar = new GregorianCalendar();
		if (calendar.get(Calendar.MINUTE) >= 30) {
			calendar.add(Calendar.MINUTE, 60 - calendar.get(Calendar.MINUTE));
		} else {
			calendar.add(Calendar.MINUTE, 30 - calendar.get(Calendar.MINUTE));
		}

		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	public void setSpinnerContent() {
		RingtoneManager manager = new RingtoneManager(getActivity());
		manager.setType(RingtoneManager.TYPE_ALARM);
		Cursor cursor = manager.getCursor();
		int num=cursor.getCount();
		if (num > 0) {
			ringtones = new String[num];
			for (int i = 0; i < num; i++) {
				list.add(manager.getRingtone(i));
				ringtones[i]=manager.getRingtone(i).getTitle(getActivity());
			}
			spinnerAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ringtones);
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			ringSpinner.setAdapter(spinnerAdapter);
		}else {
			Toast.makeText(getActivity(), "No Ringtones Found !", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根

	}

}
