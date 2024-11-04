package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.example.globals.enums.AddSingleAlarmType;
import com.example.globals.enums.BundleNames;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.helper.AddSingleAlarmListenerHelper;
import com.niemiec.reliablealarmv10.activity.alarm.add.helper.AddSingleAlarmViewHelper;
import com.niemiec.reliablealarmv10.activity.alarm.add.helper.SingleAlarmModelByViewsCreator;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddSingleAlarmActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AddSingleAlarmContractMVP.View, AddSingleAlarmListenerHelper.AddSingleAlarmActionListener {

    private AddSingleAlarmPresenter presenter;
    private AddSingleAlarmViewHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        defineBasicHeaderAppearanceData();
        viewHelper = new AddSingleAlarmViewHelper(this);
        createAddAlarmPresenter();
        presenter.downloadAlarm(Objects.requireNonNull(getIntent().getBundleExtra(BundleNames.DATA.name())));
        AddSingleAlarmListenerHelper.setupListeners(viewHelper, this);
    }

    private void defineBasicHeaderAppearanceData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }

    private void createAddAlarmPresenter() {
        presenter = new AddSingleAlarmPresenter(this);
        presenter.attach(this);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        viewHelper.setDate(year, month, day);
    }

    @Override
    public void showAlarm(SingleAlarmModel singleAlarm) {
        viewHelper.showAlarm(singleAlarm, getSupportFragmentManager());
    }

    @Override
    public SingleAlarmModel getAlarm() {
        Bundle bundle = getIntent().getBundleExtra(BundleNames.DATA.name());
        assert bundle != null;
        AddSingleAlarmType type = (AddSingleAlarmType) bundle.getSerializable(BundleNames.ADD_SINGLE_ALARM_TYPE.name());
        if (type == AddSingleAlarmType.FOR_GROUP_ALARM) {
            long groupAlarmId = bundle.getLong(BundleNames.GROUP_ALARM_ID.name());
            return SingleAlarmModelByViewsCreator.create(viewHelper.getViewManagement(), groupAlarmId);
        } else {
            return SingleAlarmModelByViewsCreator.create(viewHelper.getViewManagement());
        }
    }

    @Override
    public void goBackToPreviousActivity() {
        finish();
    }

    @Override
    public void startAlarm(SingleAlarmModel singleAlarm) {
        AlarmManagerManagement.startAlarm(singleAlarm, getApplicationContext());
    }

    @Override
    public void stopAlarm(SingleAlarmModel singleAlarm) {
        AlarmManagerManagement.stopAlarm(singleAlarm, getApplicationContext());
    }

    @Override
    public void updateNotification(List<SingleAlarmModel> activeSingleAlarms) {
        AlarmNotificationManager.updateNotification(getApplicationContext(), activeSingleAlarms);
    }

    @Override
    public void saveButtonClick() {
        presenter.saveAlarm();
    }

    @Override
    public void cancelButtonClick() {
        finish();
    }
}