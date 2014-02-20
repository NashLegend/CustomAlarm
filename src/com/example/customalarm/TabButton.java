package com.example.customalarm;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabButton extends LinearLayout {
	Button button;
	TextView textView;
	ImageView imageView;

	public TabButton(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public TabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.button_tab, this);
		button = (Button) findViewById(R.id.btnInTab);
		textView = (TextView) findViewById(R.id.txtInTab);
		imageView = (ImageView) findViewById(R.id.imgInTab);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.tabButtonView);
		String txt = array.getString(R.styleable.tabButtonView_text);
		int resId = array.getResourceId(R.styleable.tabButtonView_img, -1);
		textView.setText(txt);
		if (resId != -1) {
			imageView.setImageResource(resId);
		}
		array.recycle();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TabButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	public void setImage(int resId) {
		imageView.setImageResource(resId);
	}
}
