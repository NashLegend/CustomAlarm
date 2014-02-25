package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.model.GridData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridItem extends LinearLayout {

	private TextView tv;
	private ImageView iv;

	public GridItem(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public GridItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void inflate(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_gridview, this);
		tv = (TextView) findViewById(R.id.tag);
		iv = (ImageView) findViewById(R.id.img);
	}

	public void setData(GridData data) {
		tv.setText(data.getTag());
		iv.setImageBitmap(data.getBitmap());
	}

}
