package com.example.customalarm.ui;

import com.example.customalarm.R;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class DeleteAlarmDialog extends Dialog {

	public DeleteAlarmDialog(Context context) {
		super(context);
	}

	public DeleteAlarmDialog(Context context, int theme) {
		super(context, theme);
	}

	public DeleteAlarmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public static class Builder {
		private Context context;
		private DialogInterface.OnClickListener positiveButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
			this.positiveButtonClickListener = listener;
			return this;
		}

		public DeleteAlarmDialog create() {
			final DeleteAlarmDialog dialog = new DeleteAlarmDialog(context,R.style.Dialog);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.dialog_delete, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			((Button) layout.findViewById(R.id.button_delete))
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO 自动生成的方法存根
							positiveButtonClickListener.onClick(dialog,
									DialogInterface.BUTTON_POSITIVE);
						}
					});
			dialog.setContentView(layout);
			return dialog;
		}
	}

}
