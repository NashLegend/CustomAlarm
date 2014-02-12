package com.example.customalarm.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.customalarm.Alarm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

@SuppressLint("SimpleDateFormat")
public class AlarmHelper {
	private AlarmDBHelper dbHelper;
	private SQLiteDatabase database;
	private final Context context;

	public AlarmHelper(Context context) {
		this.context = context;
	}

	public AlarmHelper open() {
		dbHelper = new AlarmDBHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	public Long insert(Alarm alarm) {
		ContentValues values = new ContentValues();
		values.put(AlarmColumn.ALARM_ID, alarm.getId());

		values.put(AlarmColumn.ALARM_GROUPID, alarm.getGroupID());

		values.put(AlarmColumn.ALARM_CALENDAR,
				Alarm.Calendar2String(alarm.getAlarmcalendar()));

		values.put(AlarmColumn.ALARM_TYPE, String.valueOf(alarm.getType()));

		values.put(AlarmColumn.ALARM_CANCELABLE, alarm.getCancelable() ? "true" : "false");

		values.put(AlarmColumn.ALARM_TAG, alarm.getTag());

		values.put(AlarmColumn.ALARM_DAYS, Alarm.Days2String(alarm.getDays_of_some()));

		return database.insert(AlarmDBHelper.TABLE, null, values);
	}

	public ArrayList<Alarm> getAlarms() {
		ArrayList<Alarm> list = new ArrayList<Alarm>();
		String orderBy = AlarmColumn._ID + " DESC";
		Cursor cursor = database.query(AlarmDBHelper.TABLE, AlarmColumn.PROJECTION, null,
				null, null, null, orderBy);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				Bundle bundle = new Bundle();

				//ID
				bundle.putString(Alarm.ALARM_ID,
						cursor.getString(AlarmColumn.ALARM_ID_COLUMN));

				//组ID
				bundle.putString(Alarm.ALARM_GROUP_ID,
						cursor.getString(AlarmColumn.ALARM_GROUPID_COLUMN));

				//类型
				bundle.putInt(Alarm.ALARM_TYPE,
						cursor.getInt(AlarmColumn.ALARM_TYPE_COLUMN));
				
				//可取消
				bundle.putBoolean(Alarm.ALARM_CANCELABLE, "true".equals(cursor
						.getString(AlarmColumn.ALARM_CANCELABLE_COLUMN)) ? true : false);

				//标签
				bundle.putString(Alarm.ALARM_TAG,
						cursor.getString(AlarmColumn.ALARM_TAG_COLUMN));

				// 日期
				bundle.putSerializable(Alarm.ALARM_CALENDAR, Alarm.String2Calendar(cursor
						.getString(AlarmColumn.ALARM_CALENDAR_COLUMN)));

				// days_of_some
				bundle.putIntArray(Alarm.ALARM_DAYS_OF_SOME, Alarm.String2Days(cursor
						.getString(AlarmColumn.ALARM_DAYS_COLUMN)));

				Alarm alarm = new Alarm(context, bundle);
				list.add(alarm);
			}
		}
		return list;
	}

	public boolean edit(Alarm alarm) {
		ContentValues values = new ContentValues();
		values.put(AlarmColumn.ALARM_CALENDAR, alarm.getGroupID());
		values.put(AlarmColumn.ALARM_TYPE, alarm.getGroupID());
		values.put(AlarmColumn.ALARM_CANCELABLE, alarm.getGroupID());
		values.put(AlarmColumn.ALARM_TAG, alarm.getGroupID());
		values.put(AlarmColumn.ALARM_DAYS, alarm.getGroupID());
		return database.update(AlarmDBHelper.TABLE, values, AlarmColumn.ALARM_ID + " = "
				+ alarm.getId(), null) > 0;
	}

	public boolean delete(Alarm alarm) {
		return database.delete(AlarmDBHelper.TABLE,
				AlarmColumn.ALARM_ID + " = " + alarm.getId(), null) > 0;
	}

	public boolean truncate() {
		return database.delete(AlarmDBHelper.TABLE, null, null) > 0;
	}
}
