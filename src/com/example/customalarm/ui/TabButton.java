package com.example.customalarm.ui;

import com.example.customalarm.R;
import com.example.customalarm.R.id;
import com.example.customalarm.R.layout;
import com.example.customalarm.R.styleable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Pan
 *
 */
public class TabButton extends LinearLayout {
	private Button button;
	private TextView textView;
	private ImageView imageView;

	public TabButton(Context context) {
		super(context);
	}

	public TabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.button_tab, this);
		button = (Button) findViewById(R.id.btnInTab);
		//只需要setClickable(false)就可以实现按自定义容器时按钮同时出现按下效果。
		button.setClickable(false);
		textView = (TextView) findViewById(R.id.txtInTab);
		imageView = (ImageView) findViewById(R.id.imgInTab);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.tabButtonView);
		String txt = array.getString(R.styleable.tabButtonView_text);
		String btntext = array.getString(R.styleable.tabButtonView_btntext);
		int imgResId = array.getResourceId(R.styleable.tabButtonView_img, -1);
		int btnResId = array
				.getResourceId(R.styleable.tabButtonView_btnimg, -1);

		textView.setText(txt);
		if (btnResId != -1) {
			button.setBackgroundResource(btnResId);
		} else if (btntext != null) {
			button.setText(btntext);
		}

		if (imgResId != -1) {
			imageView.setImageResource(imgResId);
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
