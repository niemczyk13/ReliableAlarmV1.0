package com.niemiec.reliablealarmv10.activity.alarm.launch.safe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;

import java.util.Objects;

public class SafeAlarmActivity extends AppCompatActivity implements SafeAlarmContractMVP.View {
    private TextView alarmClockTextView;
    private TextView batteryPercentageInfoTextView;
    private TextView actualClockTextView;
    private Button okButton;
    private SafeAlarmPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_alarm);
        setWindowFlags();
        defineBasicHeaderAppearanceData();
        createSafeAlarmPresenter();
        initViews();
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

    private void createSafeAlarmPresenter() {
        presenter = new SafeAlarmPresenter(this);
        presenter.attach(this);
    }

    private void initViews() {
        alarmClockTextView = findViewById(R.id.alarm_clock_text_view);
        batteryPercentageInfoTextView = findViewById(R.id.battery_percentage_info_text_view);
        actualClockTextView = findViewById(R.id.actual_clock_text_view);
        okButton = findViewById(R.id.ok_button);
    }

    private void setListeners() {
        okButton.setOnClickListener(view -> {
            presenter.onOkButtonClick();
        });
    }

    private void setViews() {
        long id = getIntent().getBundleExtra("data").getLong("id");
        presenter.initView(id);
    }

    @Override
    public void showAlarmClock(int hour, int minute) {
        alarmClockTextView.setText(getTime(hour) + ":" + getTime(minute));
    }

    @Override
    public void showActualClock(int hour, int minute) {
        actualClockTextView.setText(getTime(hour) + ":" + getTime(minute));
    }

    @Override
    public void showBatteryPercentageInfo(String info) {
        batteryPercentageInfoTextView.setText(info);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    private String getTime(int time) {
        if (time >= 0 && time <= 9) {
            return "0" + time;
        }
        return Integer.toString(time);
    }
}