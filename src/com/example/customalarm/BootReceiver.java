package com.example.customalarm;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	public BootReceiver() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 自动生成的方法存根
		// reset alarms on boot or every day at 00:00
		ArrayList<Alarm> alarms = getAllAlarms();
		for (Alarm alarm : alarms) {
			//Test
			//当重启后，所有的都应该恢复，而如果这是定时任务，那么只要恢复月度和年度的就可以了.
			//如果每天00：00重建闹钟的话，那么00：00时响的闹钟会不会响呢
			//所以最后错开一点，因为闹钟没有秒数，所以设置为00：00：30秒何如。
			//不论是什么闹钟，都会保证如果第二天有闹钟的话就会设置上的，所以不用担心00：00的闹钟不会设置上
			alarm.activate();
		}
	}

	/**
	 * 从本地数据库恢复所有的闹钟
	 * 
	 * @return
	 */
	private ArrayList<Alarm> getAllAlarms() {
		return null;
	}

}
