package com.niemiec.reliablealarmv10.activity.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.AlarmListAdapter;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.AlarmListListener;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data.AlarmsList;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements MainContractMVP.View, AlarmListListener {
    private MainPresenter presenter;
    private AlarmListAdapter adapter;

    private ListView alarmListView;
    private FloatingActionButton addNewAlarmButton;
    private LinearLayout cancelOrDelete;
    private Button cancelDeleteAlarmButton;
    private Button deleteAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMainPresenter();
        initView();
        setListeners();
        setViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        menu.getItem(0).setOnMenuItemClickListener(item -> {
            presenter.onBinButtonClick();
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void createMainPresenter() {
        presenter = new MainPresenter(getApplicationContext());
        presenter.attach(this);
    }

    private void initView() {
        setTitle(R.string.title);
        alarmListView = findViewById(R.id.alarm_list_view);
        addNewAlarmButton = findViewById(R.id.add_alarm_button);
        cancelOrDelete = findViewById(R.id.cancel_or_delete_linear_layout);
        cancelDeleteAlarmButton = findViewById(R.id.cancel_delete_alarm_button);
        deleteAlarmButton = findViewById(R.id.delete_alarm_button);
    }

    private void setListeners() {
        cancelDeleteAlarmButton.setOnClickListener(view -> {
            presenter.onCancelButtonClick();
            alarmListView.setClickable(false);
        });

        alarmListView.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onAlarmListItemClick(position);
        });

        addNewAlarmButton.setOnClickListener(view -> {
            presenter.onCreateAlarmButtonClick();
        });

        deleteAlarmButton.setOnClickListener(view -> {
            presenter.onDeleteButtonClick(adapter.getSelectedAlarms());
        });
    }

    private void setViews() {
        presenter.initView();
    }

    //TODO po ponownym wczytaniu ustawienie normalnego włączenia - jeżeli wciśnięty kosz
    @Override
    protected void onStart() {
        super.onStart();
        //List<Alarm> alarms = AlarmDataBase.getAllAlarms();
        //adapter = new AlarmListAdapter(this, alarms);
        presenter.initView();
        alarmListView.setClickable(true);
    }


    @Override
    public void showActivity(List<Alarm> alarms) {
        changeDefaultAppSettings();
        changeTheVisibilityOfDeleteViewItems(View.GONE);
        changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        createAlarmListWithAdapter(alarms);
        adapter.showMainList();
    }

    private void changeDefaultAppSettings() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }

    private void changeTheVisibilityOfDeleteViewItems(int visibility) {
        cancelOrDelete.setVisibility(visibility);
    }

    private void changeTheVisibilityOfBrowsingViewItems(int visibility) {
        addNewAlarmButton.setVisibility(visibility);
        setTheAbilityToSelectListItems(visibility);
    }

    private void setTheAbilityToSelectListItems(int visibility) {
        if (visibility == View.VISIBLE) {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        } else {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        }
    }

    private void createAlarmListWithAdapter(List<Alarm> alarms) {
        adapter = new AlarmListAdapter(this, new AlarmsList(alarms), this);
        alarmListView.setAdapter(adapter);
    }

    @Override
    public void showAlarmListForDeletion() {
        changeTheVisibilityOfDeleteViewItems(View.VISIBLE);
        changeTheVisibilityOfBrowsingViewItems(View.GONE);
        adapter.showDeleteList();
    }

    @Override
    public void showNormalView() {
        changeTheVisibilityOfDeleteViewItems(View.GONE);
        changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        adapter.showMainList();
    }

    @Override
    public void updateAlarmList(List<Alarm> alarms) {
        createAlarmListWithAdapter(alarms);
    }

    @Override
    public void showCreateNewAlarmActivity() {
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.CREATE);
        intent.putExtra("data", bundle);

        startActivity(intent);
    }

    @Override
    public void showUpdateAlarmActivity(int position) {
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.UPDATE);
        bundle.putLong("alarm_id", adapter.getAlarm(position).id);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @Override
    public void checkOrUncheckAlarm(int position) {
        adapter.checkOnUncheckAlarm(position);
    }

    @Override
    public void startAlarm(Alarm alarm) {
        AlarmManagerManagement.startAlarm(alarm, getApplicationContext());
    }

    @Override
    public void stopAlarm(Alarm alarm) {
        AlarmManagerManagement.stopAlarm(alarm, getApplicationContext());
    }

    @Override
    public void updateNotification(List<Alarm> activeAlarms) {
        AlarmNotificationManager.updateNotification(getApplicationContext(), activeAlarms);
    }


    @Override
    public void switchOnOffClick(long id) {
        presenter.onSwitchOnOffAlarmClick(id);
    }
}