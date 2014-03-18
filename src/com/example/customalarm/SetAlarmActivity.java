
package com.example.customalarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.example.customalarm.core.Alarm;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint("SimpleDateFormat")
public class SetAlarmActivity extends Activity implements OnClickListener {

    private ActionBar mActionBar;
    private EditText tagView;
    private Button dateButton;
    private Button timeButton;
    private GregorianCalendar QCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);

        QCalendar = getCalendarAfter30Mins();
        tagView = (EditText) findViewById(R.id.TagInput);
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = df.format(QCalendar.getTime());
        dateButton.setText(dateString);

        df = new SimpleDateFormat("HH:mm");
        String timeString = df.format(QCalendar.getTime());
        timeButton.setText(timeString);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
    }

    private GregorianCalendar getCalendarAfter30Mins() {
        GregorianCalendar calendar = new GregorianCalendar();
        if (calendar.get(Calendar.MINUTE) >= 30) {
            calendar.add(Calendar.MINUTE, 60 - calendar.get(Calendar.MINUTE));
        } else {
            calendar.add(Calendar.MINUTE, 30 - calendar.get(Calendar.MINUTE));
        }

        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateButton:
                new DatePickerDialog(this, new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {
                        QCalendar.set(year, monthOfYear, dayOfMonth);
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        String dateString = df.format(QCalendar.getTime());
                        dateButton.setText(dateString);
                    }
                }, QCalendar.get(Calendar.YEAR), QCalendar.get(Calendar.MONTH),
                        QCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.timeButton:
                new TimePickerDialog(this, new OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        QCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        QCalendar.set(Calendar.MINUTE, minute);
                        DateFormat df = new SimpleDateFormat("HH:mm");
                        String timeString = df.format(QCalendar.getTime());
                        timeButton.setText(timeString);
                    }
                }, QCalendar.get(Calendar.HOUR_OF_DAY), QCalendar
                        .get(Calendar.MINUTE), true).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set_alarm, menu);
        return true;
    }

    public void done() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.save:
                Bundle bundle = new Bundle();
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                bundle.putString(Alarm.ALARM_ID, UUID.randomUUID().toString());
                bundle.putString(Alarm.ALARM_GROUP_ID, id);
                bundle.putInt(Alarm.ALARM_TYPE, Alarm.ALARM_ONE_TIME);
                bundle.putBoolean(Alarm.ALARM_CANCELABLE, true);
                String tmptagString = tagView.getText().toString().trim();
                if (tmptagString.equals("")) {
                    tmptagString = getResources().getString(R.string.Instant_Alarm);
                }
                bundle.putString(Alarm.ALARM_TAG, tmptagString);
                bundle.putSerializable(Alarm.ALARM_CALENDAR, QCalendar);
                bundle.putIntArray(Alarm.ALARM_DAYS_OF_SOME, null);
                bundle.putBoolean(Alarm.ALARM_AVAILABLE, true);
                bundle.putString(Alarm.ALARM_REMARK, "");
                bundle.putString(Alarm.ALARM_IMAGE, null);
                bundle.putString(Alarm.ALARM_GROUP_NAME, "");
                Alarm alarm = new Alarm(getApplicationContext().getApplicationContext(),
                        bundle);
                alarm.activate();
                alarm.storeInDB();
                done();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
