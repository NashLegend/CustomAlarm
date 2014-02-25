package com.example.customalarm.model;

import android.graphics.Bitmap;
import android.view.View.OnClickListener;

public class GridData {
	private Bitmap bitmap;
	private String tag;
	private OnClickListener onClickListener;

	public GridData() {

	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

}
