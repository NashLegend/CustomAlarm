package com.example.customalarm.ui;

import com.example.customalarm.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlarmSplitter extends LinearLayout {

	private ImageView leftImageView;
	private ImageView rightImageView;
	private TextView tagTextView;

	public AlarmSplitter(Context context) {
		super(context);
		inflate(context);
	}

	public AlarmSplitter(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context);
	}

	private void inflate(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.splitter_my_alarm, this);
		leftImageView = (ImageView) findViewById(R.id.splitterImgLeft);
		rightImageView = (ImageView) findViewById(R.id.splitterImgRight);
		tagTextView = (TextView) findViewById(R.id.splitterTag);
	}

	public void setLeftImage(Bitmap bitmap) {
		leftImageView.setImageBitmap(bitmap);
	}

	public void setRightImage(Bitmap bitmap) {
		rightImageView.setImageBitmap(bitmap);
	}

	public void setTagTip(String str) {
		tagTextView.setText(str);
	}

}
