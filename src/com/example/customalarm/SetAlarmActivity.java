
package com.example.customalarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.example.customalarm.core.Alarm;
import com.example.customalarm.fragments.BaseSetAlarmFragment;
import com.example.customalarm.fragments.SetCountDownAlarmFragment;
import com.example.customalarm.fragments.SetDailyAlarmFragment;
import com.example.customalarm.fragments.SetInstantAlarmFragment;
import com.example.customalarm.fragments.SetMonthlyAlarmFragment;
import com.example.customalarm.fragments.SetWeeklyAlarmFragment;
import com.example.customalarm.fragments.SetYearlyAlarmFragment;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
public class SetAlarmActivity extends Activity {

    private ActionBar mActionBar;
    private BaseSetAlarmFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);

        replaceFragment(new SetInstantAlarmFragment());
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

    public void replaceFragment(BaseSetAlarmFragment fragment) {
        currentFragment = fragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.alarm_set_window, currentFragment);
        transaction.commit();
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
                currentFragment.saveAlarm();
                done();
                break;
            case R.id.daily_set:
                if (!(currentFragment instanceof SetDailyAlarmFragment)) {
                    replaceFragment(new SetDailyAlarmFragment());
                }
                break;
            case R.id.weekly_set:
                if (!(currentFragment instanceof SetWeeklyAlarmFragment)) {
                    replaceFragment(new SetWeeklyAlarmFragment());
                }
                break;
            case R.id.monthly_set:
                if (!(currentFragment instanceof SetMonthlyAlarmFragment)) {
                    replaceFragment(new SetMonthlyAlarmFragment());
                }
                break;
            case R.id.yearly_set:
                if (!(currentFragment instanceof SetYearlyAlarmFragment)) {
                    replaceFragment(new SetYearlyAlarmFragment());
                }
                break;
            case R.id.instant_set:
                if (!(currentFragment instanceof SetInstantAlarmFragment)) {
                    replaceFragment(new SetInstantAlarmFragment());
                }
                break;
            case R.id.count_down_set:
                if (!(currentFragment instanceof SetCountDownAlarmFragment)) {
                    replaceFragment(new SetCountDownAlarmFragment());
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
