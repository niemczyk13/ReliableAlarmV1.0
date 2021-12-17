package com.niemiec.reliablealarmv10.activity.alarm.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

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

    private void createAlarmClockPresenter() {
        presenter = new AlarmClockPresenter(getApplicationContext());
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
        });

        turnOffButton.setOnClickListener(view -> {
            presenter.onTurnOffButtonClick();
        });
        //TODO dodać obsługę przcisków głośności i blokady
    }

    private void setViews() {
        long id = getIntent().getBundleExtra("data").getLong("id");
        presenter.initView(id);
    }

    @Override
    public void startAlarm(Alarm alarm) {
        AlarmManagerManagement.startAlarm(alarm, getApplicationContext());
    }

    @Override
    public void showAlarmClockWithNap(int hour, int minute) {
        clockTextView.setText(hour + ":" + minute);
        napButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAlarmClockWithoutNap(int hour, int minute) {
        clockTextView.setText(hour + ":" + minute);
        napButton.setVisibility(View.GONE);
    }
}