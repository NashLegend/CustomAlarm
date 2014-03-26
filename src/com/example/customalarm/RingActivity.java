package com.example.customalarm;

import java.io.IOException;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.db.AlarmHelper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author NashLegend 按理说只要这个页面存在，闹铃就会不断的响。 TODO 需要做闹铃的重复响
 */
public class RingActivity extends Activity implements OnClickListener {

	private Alarm alarm;
	private Intent alarmIntent;
	private Button delayButton;
	private Button stopButton;
	private TextView titleView;

	// TODO 还没有添加player的播放停止、被中止事件等等
	private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ring);
		alarmIntent = getIntent();
		AlarmHelper helper = new AlarmHelper(this);
		alarm = helper.getAlarmByID(alarmIntent.getStringExtra(Alarm.ALARM_ID));
		titleView = (TextView) findViewById(R.id.ringTags);
		titleView.setText(alarmIntent.getStringExtra(Alarm.ALARM_TAG));
		delayButton = (Button) findViewById(R.id.DelayButton);
		stopButton = (Button) findViewById(R.id.StopButton);
		delayButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);

		if (alarm != null) {
			startRing();
		} else {
		}

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
					try {
						player.reset();
						player.setDataSource(this, manager.getRingtoneUri(i));
						player.setLooping(true);
						player.prepare();
						player.start();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
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
		try {
			if (player != null && player.isPlaying()) {
				player.stop();
				player.release();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 点击停止按钮
	 */
	private void close() {
		// 停止响铃
		stopRing();
		// 如果是一次性的则删除，其他的什么也不做
		shutDown();

		// TODO
		// 如果停止延迟闹钟的话，由于intent一样，则有可能连同原有的闹铃一并删除了，但是如果闹铃是周期性的，则不应该删除。那么则在关闭闹铃后重建一次闹铃。
		Alarm.startAlarmRestore(getApplicationContext());

		finish();
	}

	private void shutDown() {
		if (alarm != null) {
			alarm.shutDown();
		}
	}

	/*
	 * 点击返回按钮退出activity。如果正在响铃，则 推迟，如果没有在响，则什么也不做，该唤醒自然会唤醒，已关闭则自然更不用管。 （非
	 * Javadoc）
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onPause() {
		try {
			if (player != null && player.isPlaying()) {
				delay();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.DelayButton:
			delay();
			break;
		case R.id.StopButton:
			close();
			break;
		default:
			break;
		}
	}

}
