package com.example.customalarm.ui;

import android.R.anim;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customalarm.AlarmDetail;
import com.example.customalarm.R;
import com.example.customalarm.adapter.MyAlarmAdapter;
import com.example.customalarm.core.Alarm;
import com.example.customalarm.interfase.BaseItem;
import com.example.customalarm.util.DisplayTools;

/**
 * @author NashLegend
 */
public class MyAlarmItem extends RelativeLayout implements BaseItem,
		OnTouchListener {
	private TextView tagTextView;
	private TextView descTextView;
	private RelativeLayout floatingLayout;
	private Alarm alarm;
	private Bundle bundle;
	private GestureDetector detector;
	private Context context;
	private MyAlarmAdapter adapter;
	private ObjectAnimator animator;
	private boolean deleting = false;

	public MyAlarmItem(Context context, MyAlarmAdapter adapter) {
		super(context);
		this.context = context;
		this.adapter = adapter;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_my_alarm, this);
		floatingLayout = (RelativeLayout) findViewById(R.id.aboveview);
		tagTextView = (TextView) findViewById(R.id.alarmtag);
		descTextView = (TextView) findViewById(R.id.alarmtime);
		setLongClickable(true);
		setOnTouchListener(this);
		detector = new GestureDetector(context, new MyAlarmGestureListener(
				context));

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
					@Override
					public void onGlobalLayout() {
						if (getWidth() > 0) {
							LayoutParams params = (LayoutParams) floatingLayout
									.getLayoutParams();
							params.width = getWidth();
							floatingLayout.setLayoutParams(params);
							if (Build.VERSION.SDK_INT < 16) {
								getViewTreeObserver()
										.removeGlobalOnLayoutListener(this);
							} else {
								getViewTreeObserver()
										.removeOnGlobalLayoutListener(this);
							}
						}
					}
				});
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO 自动生成的方法存根
		return detector.onTouchEvent(event);
	}

	public MyAlarmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyAlarmItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setDataBundle(Bundle bundle) {
		deleting = false;
		if (animator != null && animator.isStarted()) {
			animator.cancel();
		}
		floatingLayout.setX(0);
		this.bundle = bundle;
		this.alarm = new Alarm(context.getApplicationContext(), bundle);
		tagTextView.setText(alarm.getTag());
		descTextView.setText(alarm.getAlarmDescription());
	}

	public void gotoDetailActivity() {
		Intent intent = new Intent(context, AlarmDetail.class);
		intent.putExtra("alarm", MyAlarmItem.this.bundle);
		context.startActivity(intent);
	}

	public void deleteAlarm() {
		DeleteAlarmDialog.Builder builder = new DeleteAlarmDialog.Builder(
				context);
		DeleteAlarmDialog dialog = builder.setPositiveButton(
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						delete();
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}

	private void delete() {
		adapter.deleteItem(alarm.getId());
		alarm.delete();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (deleting) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	public void slideLeftToDelete() {
		deleting = true;
		animator = ObjectAnimator.ofFloat(floatingLayout, "x",
				floatingLayout.getX(), -floatingLayout.getWidth()
						- floatingLayout.getX());
		animator.setDuration(2000);
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				deleting = false;
				delete();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				deleteAlarm();
			}
		});
		animator.start();
	}

	public class MyAlarmGestureListener extends SimpleOnGestureListener {
		private Context context;

		public MyAlarmGestureListener(Context context) {
			this.context = context;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// Left Slide To Delete
			if (!deleting) {
				if (velocityX < 0 && Math.abs(velocityX / velocityY) > 3) {
					slideLeftToDelete();
				}
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			if (!deleting) {
				deleteAlarm();
			}
			super.onLongPress(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (!deleting) {
				gotoDetailActivity();
			}
			return super.onSingleTapConfirmed(e);
		}
	}

}
