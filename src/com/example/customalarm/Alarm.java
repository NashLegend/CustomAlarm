package com.example.customalarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.customalarm.db.AlarmColumn;
import com.example.customalarm.db.AlarmHelper;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Alarm {
	private String groupID;// 按理说只有weekly的能用上。不支持每月和每年同时设置几天……//完全用不上啊，可以考虑删除了
	private String id = "";// id为UUID;这可能会导致有两个完全相同时间的闹钟，而系统把它们当作两个。需要在添加的时候进行检测
	private GregorianCalendar alarmCalendar;// 响铃日期
	private int[] days_of_some;// group里面的几天
	private String tag;// example:birthday,festival or some what;
	private int type;// ALARM_DAILY,ALARM_WEEKLY等等
	private boolean cancelable = true;// 如果为true，则不可取消直到响铃完毕……
	private boolean available = true;// 是否关闭
	private String remark = null;// 备注
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
	public static final String ALARM_AVAILABLE = "ALARM_AVAILABLE";
	public static final String ALARM_REMARK = "ALARM_REMARK";

	public static final String ALARM_ACTION = "com.example.customalarm.alarm";
	public static final String RESET_ACTION = "com.example.customalarm.reset";

	public Alarm(Context context, Bundle bundle) {
		setDays_of_some(bundle.getIntArray(ALARM_DAYS_OF_SOME));
		setGroupID(bundle.getString(ALARM_GROUP_ID));
		setCancelable(bundle.getBoolean(ALARM_CANCELABLE));
		setTag(bundle.getString(ALARM_TAG));
		setType(bundle.getInt(ALARM_TYPE));
		setId(bundle.getString(ALARM_ID));
		setAlarmCalendar((GregorianCalendar) bundle
				.getSerializable(ALARM_CALENDAR));
		setAvailable(bundle.getBoolean(ALARM_AVAILABLE));
		setRemark(bundle.getString(ALARM_REMARK));
		activate();
	}

	/**
	 * 设置闹铃AlarmManager所使用的intent
	 */
	private void setIntent() {
		intent = new Intent();
		intent.putExtra(ALARM_CANCELABLE, cancelable);
		intent.putExtra(ALARM_TAG, tag);
		intent.putExtra(ALARM_TYPE, type);
		intent.putExtra(ALARM_ID, id);
		intent.putExtra(ALARM_GROUP_ID, groupID);
		intent.putExtra(ALARM_REMARK, remark);
		intent.setAction(ALARM_ACTION);
		intent.setData(Uri.fromParts("alarm", "id", id));
	}
	
	/**
	 * 如果对闹铃进行了初始化或者修改，那么就一定要调用activate()重新设置一次闹铃
	 * 不进行数据库操作，数据库的修改操作在外部进行。
	 * 当然只是设置，能不能打开还要看available
	 */
	public void activate() {
		setIntent();
		if (available) {
			setUp();
		}else {
			cancel();
		}
	}
	
	/**
	 * 保存对闹钟的修改，并运行activate
	 */
	public void edit() {
		if (Alarm.editAlarmInDB(this)) {
			activate();
		}else {
			//存储失败，要趁着intent没有修改，从intent中恢复……
		}
	}

	/**
	 * 运行闹钟
	 */
	private void setUp() {
		if (alarmCalendar != null) {
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
	}

	/**
	 * 取消闹钟，仍然保存在数据库里面，只是AlarmManager不再工作
	 * 只做Alarm工作，不处理数据库
	 */
	public void cancel() {
		setIntent();
		PendingIntent pendingIntent;
		AlarmManager manager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		//如果days_of_some不为空，则为一个闹铃组，要取消一批。否则仅仅取消一个足矣
		if (days_of_some != null && days_of_some.length > 0) {
			for (int j = 0; j < days_of_some.length; j++) {
				int k = days_of_some[j];
				pendingIntent = PendingIntent.getBroadcast(mContext, k, intent,
						PendingIntent.FLAG_CANCEL_CURRENT);
				manager.cancel(pendingIntent);
			}
		} else {
			pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent,
					PendingIntent.FLAG_CANCEL_CURRENT);
			manager.cancel(pendingIntent);
		}
	}

	/**
	 * 取消并删除闹钟
	 */
	public void delete() {
		cancel();
		deleteFromDB(this);
	}
	
	/**
	 * 设置倒计时闹钟 倒计时本质上就是一次性闹钟
	 */
	private void setCountDownAlarm() {
		setOneTimeAlarm();
	}

	/**
	 * 设置一次性闹钟
	 */
	private void setOneTimeAlarm() {
		if (alarmCalendar.getTimeInMillis() - System.currentTimeMillis() > 0) {
			// 最后一个参数必须是PendingIntent.FLAG_UPDATE_CURRENT，否则BroadcastReceiver将收不到参数。
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
	private void setDailyAlarm() {
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
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager manager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, mills, 86400000,
				pendingIntent);
	}

	/**
	 * 设置每周闹钟，每周可能有几天，如工作日，周末等等
	 */
	private void setWeeklyAlarm() {
		// 一周可能有几天，一个AlarmManager不够用，使用几个AlarmManager然后采用setRepeating就行了
		// intent必须不同，否则会被系统认为是同一个，结果是只有最后一个闹钟生效（第一个闹钟响之前设置的闹钟中）
		// 如果仅仅“extras”的不同并不会导致intent对比起来不同，所以会导致合并成一个Alarm
		// intent通过intent.filterEquals(intent2)来对比
		// 只有action, data, type, class, 和categories都相同时才会认为是一个
		// 有两个办法：一是建立data规则，通过设置不同的data判断
		// 二是设置getBroadcast中第二个参数 （int requestCode）来区分。这个参数目前API中没有使用到
		// Android URI
		// 这里采用两个都使用的方法。所有的intent都进行了intent.setData(Uri.fromParts("alarm", "id",
		// id));操作以区分开一般闹钟。
		// 而在weekly闹钟里因为设置了多个闹钟，而一个intent又只能设置一个data，所以在week里为区分不同闹钟，将使用第二种方法。
		// 将requestCode设置为day,足以分开不同的PendingIntent。以id区分外部，以requestCode区分内部
		GregorianCalendar calendar = new GregorianCalendar();
		for (int i = 0; i < days_of_some.length; i++) {
			int day = days_of_some[i];
			calendar.set(Calendar.DAY_OF_WEEK, day);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
					day, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
	private void setMonthlyAlarm() {
		// 每个月长度不一样，所以不能用setRepeating，只能设置一个月的，然后隔一段时间再重新设置一次
		GregorianCalendar calendar = new GregorianCalendar();

		int day = alarmCalendar.get(Calendar.DAY_OF_MONTH);

		long timemills = 0L;
		calendar.set(Calendar.DAY_OF_MONTH, day);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
	private void setYearlyAlarm() {
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
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GregorianCalendar getAlarmCalendar() {
		return alarmCalendar;
	}

	public void setAlarmCalendar(GregorianCalendar alarmcalendar) {
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
		if (days_of_some == null) {
			days_of_some = new int[0];
		}
		this.days_of_some = days_of_some;
	}

	public boolean isAvailable() {
		return available;
	}

	/**
	 * 仅仅修改available值，并不实际的打开或者关闭闹钟
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 存储到数据库，只在用户创建时调用一次
	 */
	public static void storeInDB(Alarm alarm) {
		AlarmHelper helper = new AlarmHelper(alarm.getmContext());
		helper.insert(alarm);
	}

	/**
	 * 从数据库删除
	 */
	public static void deleteFromDB(Alarm alarm) {
		AlarmHelper helper = new AlarmHelper(alarm.getmContext());
		helper.delete(alarm);
	}

	/**
	 * 在数据库中修改
	 */
	public static boolean editAlarmInDB(Alarm alarm) {
		AlarmHelper helper = new AlarmHelper(alarm.getmContext());
		return helper.edit(alarm);
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
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		// manager.cancel(pendingIntent);//到底要不要先取消，按理说不要
		// 应该是每天00：00检查一次
		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 86400000, pendingIntent);
	}

	public static GregorianCalendar String2Calendar(String string) {
		// 日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = null;
		try {
			calendar = new GregorianCalendar();
			Date date = format.parse(string);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
			calendar = null;
		}
		return calendar;
	}

	public static String Calendar2String(GregorianCalendar calendar) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(calendar.getTime());
	}

	public static String Days2String(int[] days) {
		String dayString = "";
		if (days != null && days.length > 0) {
			for (int i = 0; i < days.length; i++) {
				String j = String.valueOf(days[i]);
				dayString += j + "_";
			}
		}
		return dayString;
	}

	public static int[] String2Days(String dayString) {
		int[] ints = null;
		if (dayString != null && dayString.length() > 0) {
			String[] strs = dayString.split("_");
			if (strs != null && strs.length > 0) {
				ints = new int[strs.length];
				for (int i = 0; i < strs.length; i++) {
					int j = Integer.parseInt(strs[i]);
					ints[i] = j;
				}
			}
		}
		if (ints == null) {
			ints = new int[0];
		}
		return ints;
	}

}
