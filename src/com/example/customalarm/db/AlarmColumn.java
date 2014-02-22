package com.example.customalarm.db;

import android.provider.BaseColumns;

public class AlarmColumn implements BaseColumns {

	public static final String ALARM_ID = "ALARM_ID";
	public static final String ALARM_GROUPID = "ALARM_GROUPID";
	public static final String ALARM_CALENDAR = "ALARM_CALENDAR";
	public static final String ALARM_TYPE = "ALARM_TYPE";
	public static final String ALARM_CANCELABLE = "ALARM_CANCELABLE";
	public static final String ALARM_TAG = "ALARM_TAG";
	public static final String ALARM_DAYS = "ALARM_DAYS";
	public static final String ALARM_AVAILABLE = "ALARM_AVAILABLE";
	public static final String ALARM_REMARK = "ALARM_REMARK";
	public static final String ALARM_IMAGE = "ALARM_IMAGE";
	public static final String ALARM_GROUPNAME = "ALARM_GROUPNAME";

	public static final int ALARM_ID_COLUMN = 1;
	public static final int ALARM_GROUPID_COLUMN = 2;
	public static final int ALARM_CALENDAR_COLUMN = 3;
	public static final int ALARM_TYPE_COLUMN = 4;
	public static final int ALARM_CANCELABLE_COLUMN = 5;
	public static final int ALARM_TAG_COLUMN = 6;
	public static final int ALARM_DAYS_COLUMN = 7;
	public static final int ALARM_AVAILABLE_COLUMN = 8;
	public static final int ALARM_REMARK_COLUMN = 9;
	public static final int ALARM_IMAGE_COLUMN = 10;
	public static final int ALARM_GROUPNAME_COLUMN = 11;

	// 查询结果集
	public static final String[] PROJECTION = { _ID, ALARM_ID, ALARM_GROUPID,
			ALARM_CALENDAR, ALARM_TYPE, ALARM_CANCELABLE, ALARM_TAG, ALARM_DAYS,
			ALARM_AVAILABLE, ALARM_REMARK, ALARM_IMAGE, ALARM_GROUPNAME };

}
