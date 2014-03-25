package com.example.customalarm;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.db.AlarmHelper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * @author NashLegend 按理说只要这个页面存在，闹铃就会不断的响。
 *  TODO 需要做闹铃的重复响。add 闹铃是完全无限循环响的，所以不必响应这个事件,只需响应按钮和返回即可
 */
public class RingActivity extends Activity {

	private Alarm alarm;
	private Intent alarmIntent;
	// TODO 还没有添加ringtone的播放停止、被中止事件等等
	private Ringtone ringtone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ring);
		alarmIntent = getIntent();
		AlarmHelper helper = new AlarmHelper(this);
		alarm = helper.getAlarmByID(alarmIntent.getStringExtra(Alarm.ALARM_ID));
		if (alarm != null) {

		} else {
			// alarm is null
		}

		startRing();
	}

	private void startRing() {
		RingtoneManager manager = new RingtoneManager(this);
		manager.setType(RingtoneManager.TYPE_ALARM);
		Cursor cursor = manager.getCursor();
		int num = cursor.getCount();
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				Ringtone tmpRingtone = manager.getRingtone(i);
				if (tmpRingtone.getTitle(this).equals(
						alarmIntent.getStringExtra(Alarm.ALARM_RINGTONE))) {
					ringtone = tmpRingtone;
					// TODO 需要做闹铃的重复响。直到页面关闭或者点击推迟或者关闭按钮
					ringtone.play();
					break;
				}
			}
		} else {
			Toast.makeText(this, "No Ringtones Found !", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 点击推迟按钮，推迟到10分钟后再响
	 */
	private void delay() {
		// 先停止响铃
		stopRing();
		// 再设置推迟闹铃
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getApplicationContext(), 0, getIntent(),
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager manager = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + 600000l, pendingIntent);

		finish();
	}

	private void stopRing() {
		if (ringtone != null && ringtone.isPlaying()) {
			ringtone.stop();
		}
	}

	/**
	 * 点击停止按钮
	 */
	public void close() {
		// 停止响铃
		stopRing();
		// 如果是一次性的则删除，其他的什么也不做
		alarm.shutDown();

		// TODO
		// 如果停止延迟闹钟的话，由于intent一样，则有可能连同原有的闹铃一并删除了，但是如果闹铃是周期性的，则不应该删除。那么则在关闭闹铃后重建一次闹铃。
		Alarm.startAlarmRestore(getApplicationContext());

		finish();
	}

	public void shutDown() {
		if (alarm != null) {
			alarm.shutDown();
		}
	}

	/**
	 * 播放正常完毕，则延迟10分钟 TODO 需要与异常关闭区分开来
	 * 闹铃是完全无限循环响的，所以不必响应这个事件
	 */
	private void onPlay2End() {
		delay();
	}

	/**
	 * 播放被人为中断。不做处理，有可能是退出Activity、点击推迟按钮推迟Alarm、点击终止按钮终止Alarm。 TODO
	 * 需要与正常关闭相区分开来
	 */
	private void onPlayInterrupt() {

	}

	/*
	 * 点击返回按钮退出activity。如果正在响铃，则 推迟，如果没有在响，则什么也不做，该唤醒自然会唤醒，已关闭则自然更不用管。 （非
	 * Javadoc）
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onPause() {
		if (ringtone != null && ringtone.isPlaying()) {
			delay();
		}
		super.onPause();
	}

}
