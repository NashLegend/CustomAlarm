package com.example.customalarm;

import com.example.customalarm.adapter.ViewPagerAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.fragment.MyAlarmFragment;
import com.example.customalarm.ui.CustomViewPager;
import com.example.customalarm.ui.TabButton;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * @author NashLegend I put this as minority
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private CustomViewPager viewPager;
	private ViewPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Alarm.startAlarmRestore(getApplicationContext());
		initView();
	}

	private void initView() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.MainContent, new MyAlarmFragment());
		transaction.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

	}
}
