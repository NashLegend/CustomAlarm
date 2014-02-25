package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.model.GridData;
import com.example.customalarm.util.DisplayTools;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class GridPanel extends RelativeLayout {

	public GridPanel(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public GridPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setData(GridData[] datas) {
		int dim = (int) (getResources().getDimension(R.dimen.grid_item_dim));
		int wid = DisplayTools.getScreenWidth(getContext());
		int hei = DisplayTools.getWindowHeight((Activity) getContext());
		int col = (int) (wid / dim);
		int lines = (int) Math.ceil(hei / dim);
		int marginH = (DisplayTools.getScreenWidth(getContext()) - dim * col) / (col * 2);
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < col; j++) {
				if (lines * i + j < datas.length) {
					GridItem item = new GridItem(getContext());
					GridData data = datas[lines * i + j];
					item.setData(data);
					LayoutParams params = new LayoutParams(dim, dim);
					params.leftMargin = (2 * j + 1) * marginH + j * dim;
					params.topMargin = i * dim;
					item.setLayoutParams(params);
					addView(item);
				} else {
					break;
				}
			}
		}
		requestLayout();
	}
}
