package com.example.customalarm.ui;

import com.example.customalarm.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * @author Pan
 *
 */
public class RecommendedAlarmItem extends RelativeLayout {

	public RecommendedAlarmItem(Context context) {
		super(context);
	}

	public RecommendedAlarmItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_recommended_alarm, this);
	}

	public RecommendedAlarmItem(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

}
