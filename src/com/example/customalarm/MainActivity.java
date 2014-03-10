package com.example.customalarm;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.fragment.LeftMenu;
import com.example.customalarm.fragment.MainContent;
import com.example.customalarm.fragment.RightMenu;
import com.example.customalarm.util.DisplayTools;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * Deprecated
 * @author NashLegend
 * I put this as minority
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private SlidingMenu menu;

	public SlidingMenu getMenu() {
		return menu;
	}

	public void setMenu(SlidingMenu menu) {
		this.menu = menu;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Alarm.startAlarmRestore(getApplicationContext());
		initSlidingMenu();
	}
	
	private void initSlidingMenu() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new MainContent()).commit();
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setBehindOffset(DisplayTools.dip2px(64, this));//

		menu.setShadowWidth(20);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setSecondaryShadowDrawable(R.drawable.secondaryshadow);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

		menu.setMenu(R.layout.menu_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.LeftMenu, new LeftMenu()).commit();
		menu.setSecondaryMenu(R.layout.menu_right);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.RightMenu, new RightMenu()).commit();
	}

	public void switchContent(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment)
				.commit();
	}
	
	public void showLeft() {
		if (menu!=null) {
			menu.showMenu();
		}
	}
	
	public void showRight() {
		if (menu!=null) {
			menu.showSecondaryMenu();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根

	}

}
