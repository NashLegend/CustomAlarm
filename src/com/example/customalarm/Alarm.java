package com.example.customalarm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.R.string;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Alarm {
	private String groupID;// weekly or monthly or yearly alarm will use this to
							// get a group of alarms together
	private String id = "";// if group has only one member,this will equal with
							// groupID
	private GregorianCalendar alarmCalendar;
	private int[] days_of_some;// this is ordered outside before it was
								// delivered here
	private String tag;// example:birthday,festival or some what;
	private int type;// ALARM_DAILY,ALARM_WEEKLY,etc..
	private boolean cancelable = true;// if set false,the alarm won't be
										// canceled when ringing.
	private Context mContext;
	private Intent intent;

	public static final int ALARM_DAILY = 1;
	public static final int ALARM_WEEKLY = 2;
	public static final int ALARM_MONTHLY = 4;
	public static final int ALARM_YEARLY = 8;
	public static final int ALARM_ONE_TIME = 16;
	public static final int ALARM_COUNT_DOWN = 32;

	public static final String ALARM_ID = "ALARM_ID";
	public static final String ALARM_TYPE = "ALARM_TYPE";
	public static final String ALARM_TAG = "ALARM_TAG";
	public static final String ALARM_CANCELABLE = "ALARM_CANCELABLE";
	public static final String ALARM_GROUP_ID = "ALARM_GROUP_ID";
	public static final String ALARM_DAYS_OF_SOME = "ALARM_DAYS_OF_SOME";
	
	public static final String ALARM_ACTION="com.example.customalarm.alarm";
	public static final String RESET_ACTION="com.example.customalarm.reset";

	public Alarm(Context context, Bundle bundle) {

		setDays_of_some(bundle.getIntArray(ALARM_DAYS_OF_SOME));
		setGroupID(bundle.getString(ALARM_GROUP_ID));
		setCancelable(bundle.getBoolean(ALARM_CANCELABLE));
		setTag(bundle.getString(ALARM_TAG));
		setType(bundle.getInt(ALARM_TYPE));
		setIdentifier(bundle.getString(ALARM_ID));

		intent = new Intent();
		intent.putExtra(ALARM_CANCELABLE, cancelable);
		intent.putExtra(ALARM_TAG, tag);
		intent.putExtra(ALARM_TYPE, type);
		intent.putExtra(ALARM_ID, id);
		intent.putExtra(ALARM_GROUP_ID, groupID);
		intent.putExtra(ALARM_DAYS_OF_SOME, days_of_some);// In fact, no need to
															// put this

		intent.setAction("com.example.customalarm.alarm");

		switch (type) {
		case ALARM_DAILY:
			setDailyAlarm();
			break;
		case ALARM_WEEKLY:
			setWeeklyAlarm();
			break;
		case ALARM_MONTHLY:
			setMonthlyAlarm();
			break;
		case ALARM_YEARLY:
			setYearlyAlarm();
			break;
		case ALARM_ONE_TIME:
			setOneTimeAlarm();
			break;
		case ALARM_COUNT_DOWN:
			setCountDownAlarm();
			break;

		default:
			break;
		}
	}

	public void setCountDownAlarm() {
		// A count-down-alarm will also happen at some date ,therefore this
		// equals with the method setOneTimeAlarm
		// The only difference is the type
		setOneTimeAlarm();
	}

	public void setOneTimeAlarm() {
		if (alarmCalendar.getTimeInMillis() - System.currentTimeMillis() > 0) {
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent,
					0);
			AlarmManager manager = (AlarmManager) mContext
					.getSystemService(Context.ALARM_SERVICE);
			manager.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(),
					pendingIntent);
		} else {
			Toast.makeText(mContext, "Too early that alarm won't alarm",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void setDailyAlarm() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, alarmCalendar.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, alarmCalendar.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, alarmCalendar.get(Calendar.SECOND));
		long mills = 0L;
		if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
			// Rings today
			mills = calendar.getTimeInMillis();
		} else {
			// Rings tomorrow
			mills = calendar.getTimeInMillis() + 86400000;
		}

		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		AlarmManager manager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, mills, 86400000, pendingIntent);
	}

	public void setWeeklyAlarm() {
		// This may happens once or more(less than seven) times
		// but this can be divided into several different
		// the only two differences between them is the DAY_OF_WEEK(from 1 to 7)
		// and id
		// First day is Sunday
		GregorianCalendar calendar = new GregorianCalendar();
		for (int i = 0; i < days_of_some.length; i++) {
			int day = days_of_some[i];
			calendar.set(Calendar.DAY_OF_WEEK, day);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent,
					0);
			AlarmManager manager = (AlarmManager) mContext
					.getSystemService(Context.ALARM_SERVICE);
			if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
				manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
						86400000 * 7, pendingIntent);
			} else {
				manager.setRepeating(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis() + 86400000 * 7, 86400000 * 7,
						pendingIntent);
			}
		}
	}

	public void setMonthlyAlarm() {
		//The number of days in every month is not always the same 
	}

	public void setYearlyAlarm() {
		//The number of days in every month is not always the same 
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public String getIdentifier() {
		return id;
	}

	public void setIdentifier(String identifier) {
		this.id = identifier;
	}

	public GregorianCalendar getAlarmcalendar() {
		return alarmCalendar;
	}

	public void setAlarmcalendar(GregorianCalendar alarmcalendar) {
		this.alarmCalendar = alarmcalendar;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public int[] getDays_of_some() {
		return days_of_some;
	}

	public void setDays_of_some(int[] days_of_some) {
		this.days_of_some = days_of_some;
	}

}
