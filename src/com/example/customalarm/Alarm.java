package com.example.customalarm;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Alarm {
	private String groupID;// 按理说只有weekly的能用上。不支持每月和每年同时设置几天……，除非是蓝翔校长，每月8号、18号、28号开学
	private String id = "";// id为UUID;这可能会导致有两个完全相同时间的闹钟，而系统把它们当作两个。需要在添加的时候进行检测
	private GregorianCalendar alarmCalendar;// 响铃日期
	private int[] days_of_some;// group里面的几天
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
	public static final String ALARM_CALENDAR = "ALARM_CALENDAR";
	public static final String ALARM_TAG = "ALARM_TAG";
	public static final String ALARM_CANCELABLE = "ALARM_CANCELABLE";
	public static final String ALARM_GROUP_ID = "ALARM_GROUP_ID";
	public static final String ALARM_DAYS_OF_SOME = "ALARM_DAYS_OF_SOME";

	public static final String ALARM_ACTION = "com.example.customalarm.alarm";
	public static final String RESET_ACTION = "com.example.customalarm.reset";

	public Alarm(Context context, Bundle bundle) {
		setDays_of_some(bundle.getIntArray(ALARM_DAYS_OF_SOME));
		setGroupID(bundle.getString(ALARM_GROUP_ID));
		setCancelable(bundle.getBoolean(ALARM_CANCELABLE));
		setTag(bundle.getString(ALARM_TAG));
		setType(bundle.getInt(ALARM_TYPE));
		setIdentifier(bundle.getString(ALARM_ID));
		setAlarmcalendar((GregorianCalendar) bundle
				.getSerializable(ALARM_CALENDAR));
	}

	/**
	 * 设定闹钟
	 */
	public void setUp() {
		intent = new Intent();
		intent.putExtra(ALARM_CANCELABLE, cancelable);
		intent.putExtra(ALARM_TAG, tag);
		intent.putExtra(ALARM_TYPE, type);
		intent.putExtra(ALARM_ID, id);
		intent.putExtra(ALARM_GROUP_ID, groupID);
		intent.setAction(ALARM_ACTION);

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

	/**
	 * 存储到数据库
	 */
	public void storeInDB() {

	}

	/**
	 * 设置倒计时闹钟 倒计时本质上就是一次性闹钟
	 */
	public void setCountDownAlarm() {
		setOneTimeAlarm();
	}

	/**
	 * 设置一次性闹钟
	 */
	public void setOneTimeAlarm() {
		if (alarmCalendar.getTimeInMillis() - System.currentTimeMillis() > 0) {
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
					0, intent, 0);
			AlarmManager manager = (AlarmManager) mContext
					.getSystemService(Context.ALARM_SERVICE);
			manager.set(AlarmManager.RTC_WAKEUP,
					alarmCalendar.getTimeInMillis(), pendingIntent);
		} else {
			Toast.makeText(mContext, "Too early that alarm won't alarm",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 设置每日闹钟
	 */
	public void setDailyAlarm() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY,
				alarmCalendar.get(Calendar.HOUR_OF_DAY));
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

		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
				intent, 0);
		AlarmManager manager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, mills, 86400000,
				pendingIntent);
	}

	/**
	 * 设置每周闹钟，每周可能有几天，如工作日，周末等等
	 */
	public void setWeeklyAlarm() {
		// 一周可能有几天，一个AlarmManager不够用，使用几个AlarmManager然后采用setRepeating就行了
		GregorianCalendar calendar = new GregorianCalendar();
		for (int i = 0; i < days_of_some.length; i++) {
			int day = days_of_some[i];
			calendar.set(Calendar.DAY_OF_WEEK, day);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
					0, intent, 0);
			AlarmManager manager = (AlarmManager) mContext
					.getSystemService(Context.ALARM_SERVICE);
			if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
				manager.setRepeating(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis(), 86400000 * 7, pendingIntent);
			} else {
				manager.setRepeating(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis() + 86400000 * 7,
						86400000 * 7, pendingIntent);
			}
		}
	}

	/**
	 * 设置月度闹钟，一次设置一个
	 */
	public void setMonthlyAlarm() {
		// 每个月长度不一样，所以不能用setRepeating，只能设置一个月的，然后隔一段时间再重新设置一次
		GregorianCalendar calendar = new GregorianCalendar();

		int day = alarmCalendar.get(Calendar.DAY_OF_MONTH);

		long timemills = 0L;
		calendar.set(Calendar.DAY_OF_MONTH, day);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
				intent, 0);
		AlarmManager manager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		// 查检日期是否合法，不合法则表示本月没有这一天，day将肯定大于28，本次将不再设定此闹铃，将闹铃的设置推迟到下次BootReceiver接收到消息
		if (isValidDate(calendar)) {
			if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
				timemills = calendar.getTimeInMillis();
				manager.set(AlarmManager.RTC_WAKEUP, timemills, pendingIntent);
			} else {
				// 下月今天也可能不合法，如1月31日设置的每月30日的月度闹钟
				calendar = getNextMonthCalendar(calendar);
				if (isValidDate(calendar)) {
					timemills = calendar.getTimeInMillis();
					manager.set(AlarmManager.RTC_WAKEUP, timemills,
							pendingIntent);
				}
			}
		}
	}

	/**
	 * 设置年度闹钟，一次只能设置一个
	 */
	public void setYearlyAlarm() {
		// The number of days in every month is not always the same
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, alarmCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				alarmCalendar.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				alarmCalendar.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, alarmCalendar.get(Calendar.MINUTE));

		// 在12月31号，闹钟为1月1号，则此if不成立，因为闹钟将发生在明年也就是明天，此时最好是设置上
		// 设定为期一年的时间太长，一般手机都会关过几次机。如果很快就到下一年了，则只设置下一年的距今30天以内的。
		// 反正每天都会定时检查一下闹钟
		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
				intent, 0);
		AlarmManager manager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
			manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					pendingIntent);
		} else {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			if (calendar.getTimeInMillis() - System.currentTimeMillis() > 86400000 * 30) {
				manager.set(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis(), pendingIntent);
			}
		}
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

	/**
	 * 获得下一个月的现在的日期，有可能是不合法的
	 * 
	 * @param calendar
	 * @return
	 */
	public static GregorianCalendar getNextMonthCalendar(
			GregorianCalendar calendar) {
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
		} else {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		}
		return calendar;
	}

	/**
	 * 检查日期是否合法
	 * 
	 * @param calendar
	 * @return
	 */
	public static boolean isValidDate(GregorianCalendar calendar) {
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			if (day < 32) {
				return true;
			}
		}

		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day < 31) {
				return true;
			}
		}

		if (month == 2) {
			if (calendar.isLeapYear(calendar.get(Calendar.YEAR))) {
				if (day < 30) {
					return true;
				}
			} else {
				if (day < 29) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 定时唤起检查闹铃的任务
	 */
	public static void startAlarmRestore(Context context) {
		Intent intent = new Intent();
		intent.setAction(Alarm.ALARM_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		// manager.cancel(pendingIntent);//到底要不要先取消，按理说不要
		// 应该是每天00：00检查一次
		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 86400000, pendingIntent);
	}

}
