package com.niemiec.reliablealarmv10.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.AlarmListAdapter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements MainContractMVP.View, AlarmListAdapter.AlarmListContainer {
    private MainPresenter presenter;
    private AlarmListAdapter adapter;

    private ImageButton binImageButton;
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

    private void createMainPresenter() {
        presenter = new MainPresenter(getApplicationContext());
        presenter.attach(this);
    }

    private void initView() {
        binImageButton = findViewById(R.id.bin_image_button);
        alarmListView = findViewById(R.id.alarm_list_view);
        addNewAlarmButton = findViewById(R.id.add_alarm_button);
        cancelOrDelete = findViewById(R.id.cancel_or_delete_linear_layout);
        cancelDeleteAlarmButton = findViewById(R.id.cancel_delete_alarm_button);
        deleteAlarmButton = findViewById(R.id.delete_alarm_button);
    }

    //TODO
    private void setListeners() {
        binImageButton.setOnClickListener(v -> {
            presenter.onBinButtonClick();
        });

        cancelDeleteAlarmButton.setOnClickListener(view -> {
            presenter.onCancelButtonClick();
        });

        alarmListView.setOnItemClickListener((parent, view, position, id) -> {
            if (alarmListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {


                //TODO zmiana widoku -> przycisku anuluj i usuń
                //TODO Zaznacz wszystkie i ilość zaznaczonych
            } else {
                Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("type", AddAlarmPresenter.Type.UPDATE);
                bundle.putLong("alarm_id", adapter.getAlarm(position).id);
                intent.putExtra("data", bundle);

                startActivity(intent);
            }
        });

        addNewAlarmButton.setOnClickListener(view -> {
            presenter.onCreateAlarmButtonClick();
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
    }


    @Override
    public void showActivity(List<Alarm> alarms) {
        changeDefaultAppSettings();
        changeTheVisibilityOfDeleteViewItems(View.GONE);
        changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        createAlarmListAdapter(alarms);
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
        binImageButton.setVisibility(visibility);
        setTheAbilityToSelectListItems(visibility);
    }

    private void setTheAbilityToSelectListItems(int visibility) {
        if (visibility == View.VISIBLE) {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        } else {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        }
    }

    private void createAlarmListAdapter(List<Alarm> alarms) {
        adapter = new AlarmListAdapter(this, alarms, this);
        alarmListView.setAdapter(adapter);
    }

    //TODO
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
        adapter = new AlarmListAdapter(this, alarms, this);
        alarmListView.setAdapter(adapter);
    }

    @Override
    public void showCreateNewAlarmActivity() {
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.CREATE);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

}