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

public class MainActivity extends AppCompatActivity implements MainContractMVP.View {
    private MainPresenter presenter;
    private AlarmListAdapter adapter;

    private ImageButton binImageButton;
    private ListView alarmListView;
    private FloatingActionButton addNewAlarmButton;
    private LinearLayout cancelOrDelete;
    private Button cancelDeleteAlarmButton;
    private Button deleteAlarmButton;

    private Set<Integer> selectedAlarms = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);

        createMainPresenter();
        initView();
        //TODO
        setListeners();
        //TODO
        setViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //params.width = 0;
        //cancelOrDelete.setLayoutParams(params);
        cancelOrDelete.setVisibility(View.GONE);

        //List<Alarm> alarms = AlarmDataBase.getAllAlarms();

        //adapter = new AlarmListAdapter(this, alarms);

        alarmListView.setAdapter(adapter);


        binImageButton.setOnClickListener(v -> {
            if (alarmListView.getChoiceMode() != AbsListView.CHOICE_MODE_MULTIPLE) {
                alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                //TODO pokazanie przucisków
                cancelOrDelete.setVisibility(View.VISIBLE);
                addNewAlarmButton.setVisibility(View.GONE);
                binImageButton.setVisibility(View.GONE);
            } else {
                alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                selectedAlarms.clear();
                cancelOrDelete.setVisibility(View.GONE);
                addNewAlarmButton.setVisibility(View.VISIBLE);
                binImageButton.setVisibility(View.VISIBLE);
            }
        });

        cancelDeleteAlarmButton.setOnClickListener(view -> {
            binImageButton.callOnClick();
        });

        alarmListView.setOnItemClickListener((parent, view, position, id) -> {
            if (alarmListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {


                //TODO zmiana widoku -> przycisku anuluj i usuń
                //TODO Zaznacz wszystkie i ilość zaznaczonych
            } else {
                Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("type", AddAlarmPresenter.Type.UPDATE);
                //bundle.putLong("alarm_id", alarms.get(position).id);
                intent.putExtra("data", bundle);

                startActivity(intent);
            }
        });

        //TODO dodanie obsługi:
        //kliknięcie w kosz
        //kliknięcie w alarm --> edycja alarmu ("update" i "id")
        //kliknięcie w alarm podczas kosza --> zaznaczenie
        //podczas kliknięcie w kosz:
        // * aktualizacja listy
        // * pojawienie się przycisków anuluj i usuń
        // * gdy kliknięto usuń usuwa zaznaczone
        //TODO kliknięcie w przycisk dodawania nowego alarmu ("create")
        getAddNewAlarmButton();
    }

    //TODO
    private void setListeners() {
    }

    private void setViews() {
        //TODO
    }

    private void initView() {
        binImageButton = findViewById(R.id.bin_image_button);
        alarmListView = findViewById(R.id.alarm_list_view);
        cancelOrDelete = findViewById(R.id.cancel_or_delete_linear_layout);
        cancelDeleteAlarmButton = findViewById(R.id.cancel_delete_alarm_button);
        deleteAlarmButton = findViewById(R.id.delete_alarm_button);
    }

    private void getAddNewAlarmButton() {
        addNewAlarmButton = findViewById(R.id.add_alarm_button);
        addNewAlarmButton.setOnClickListener(this::addNewAlarmButtonClick);
    }

    private void addNewAlarmButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.CREATE);
        intent.putExtra("data", bundle);

        startActivity(intent);
    }

    private void createMainPresenter() {
        presenter = new MainPresenter(getApplicationContext());
        presenter.attach(this);
    }

    //TODO po ponownym wczytaniu ustawienie normalnego włączenia - jeżeli wciśnięty kosz
    @Override
    protected void onStart() {
        super.onStart();
        //List<Alarm> alarms = AlarmDataBase.getAllAlarms();

        //adapter = new AlarmListAdapter(this, alarms);
        alarmListView.setAdapter(adapter);
    }


    @Override
    public void showMainAlarmList(List<Alarm> alarms) {

    }

    //TODO
    @Override
    public void showAlarmListForDeletion() {

    }

    @Override
    public void updateAlarmList(List<Alarm> alarms) {

    }

    @Override
    public void showNewActivity(Intent intent) {

    }
}