package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.model.GridData;
import com.example.customalarm.util.DisplayTools;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class GridView extends LinearLayout {

	public GridView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public GridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setData(GridData[] datas) {
		int dim = (int) (getResources().getDimension(R.dimen.grid_item_dim));
		int wid = DisplayTools.getScreenWidth(getContext());
		int hei = DisplayTools.getWindowHeight((Activity) getContext());
		int col = (int) (wid / dim);
		int lines = (int) Math.ceil(hei / dim);
		int tot = col * lines;
		int numPanel = (int) Math.ceil((float) (datas.length) / tot);
		for (int i = 0; i < numPanel; i++) {
			int n = tot;
			if (i == numPanel) {
				n = datas.length - i * tot;
			}
			GridData[] tmpDatas = new GridData[n];
			for (int j = 0; j < n; j++) {
				tmpDatas[j] = datas[i * tot + j];
			}
			GridPanel panel=new GridPanel(getContext());
			LayoutParams params=new LayoutParams(getWidth(), getHeight());
			panel.setData(tmpDatas);
			addView(panel);
		}
	}

}
