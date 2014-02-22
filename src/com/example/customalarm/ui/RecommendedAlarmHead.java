package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.interfase.BaseItem;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * @author Pan
 *
 */
public class RecommendedAlarmHead extends RelativeLayout implements BaseItem {

	public RecommendedAlarmHead(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_header_recommended_alarm, this);
	}

	public RecommendedAlarmHead(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RecommendedAlarmHead(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void setDataBundle(Bundle bundle) {
		// TODO 自动生成的方法存根
		
	}

}
