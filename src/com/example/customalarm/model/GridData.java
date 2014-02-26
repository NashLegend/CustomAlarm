package com.example.customalarm.model;

import android.view.View.OnClickListener;

public class GridData {
	private int imgId;
	private String tag;
	private OnClickListener onClickListener;

	public GridData(String tagString, int imageId) {
		tag = tagString;
		imgId = imageId;
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

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

}
