package com.niemiec.reliablealarmv10.activity.singleAlarm.launch.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.singleAlarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.singleAlarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmClockActivity extends AppCompatActivity implements AlarmClockContractMVP.View {
    private AlarmClockPresenter presenter;
    private TextView clockTextView;
    private Button napButton;
    private Button turnOffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);
        setWindowFlags();
        defineBasicHeaderAppearanceData();
        createAlarmClockPresenter();
        initView();
        setListeners();
        setViews();
    }

    private void setWindowFlags() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void defineBasicHeaderAppearanceData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            presenter.onPowerKeyClick();
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createAlarmClockPresenter() {
        presenter = new AlarmClockPresenter(this);
        presenter.attach(this);
    }

    private void initView() {
        clockTextView = findViewById(R.id.clock_text_view);
        napButton = findViewById(R.id.nap_button);
        turnOffButton = findViewById(R.id.turn_off_the_alarm_button);
    }

    private void setListeners() {
        napButton.setOnClickListener(view -> {
            presenter.onNapButtonClick();
            finish();
//            android.os.Process.killProcess(android.os.Process.myPid());
        });

        turnOffButton.setOnClickListener(view -> {
            presenter.onTurnOffButtonClick();
            finish();
//            android.os.Process.killProcess(android.os.Process.myPid());

        });
        //TODO dodać obsługę przcisków głośności i blokady
    }

    private void setViews() {
        long id = getIntent().getBundleExtra("data").getLong("id");
        presenter.initView(id);
    }

    @Override
    public void startAlarm(SingleAlarm singleAlarm) {
        AlarmManagerManagement.startAlarm(singleAlarm, getApplicationContext());
    }

    @Override
    public void showAlarmClockWithNap(int hour, int minute) {
        showHourAndMinute(hour, minute);
        napButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAlarmClockWithoutNap(int hour, int minute) {
        showHourAndMinute(hour, minute);
        napButton.setVisibility(View.GONE);
    }

    @Override
    public void updateNotification(List<SingleAlarm> activeSingleAlarms) {
        AlarmNotificationManager.updateNotification(getApplicationContext(), activeSingleAlarms);
    }

    private void showHourAndMinute(int hour, int minute) {
        clockTextView.setText(getTime(hour) + ":" + getTime(minute));
    }

    private String getTime(int time) {
        if (time >= 0 && time <= 9) {
            return "0" + time;
        }
        return Integer.toString(time);
    }
}