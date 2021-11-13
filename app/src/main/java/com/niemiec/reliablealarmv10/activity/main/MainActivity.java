package com.niemiec.reliablealarmv10.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.AlarmListAdapter;
import com.niemiec.reliablealarmv10.activity.main.view.toggle.ToggleBetweenDeleteAndEditViews;
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

        binImageButton = findViewById(R.id.bin_image_button);
        alarmListView = findViewById(R.id.alarm_list_view);

        List<Alarm> alarms = AlarmDataBase.getAllAlarms();

        adapter = new AlarmListAdapter(this, alarms);

        alarmListView.setAdapter(adapter);

        ToggleBetweenDeleteAndEditViews toggle = new ToggleBetweenDeleteAndEditViews();
        toggle.attach(adapter);

        binImageButton.setOnClickListener(v -> {
            toggle.toggleView();
            if (alarmListView.getChoiceMode() != AbsListView.CHOICE_MODE_MULTIPLE) {
                alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            } else {
                alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
            }
        });

        alarmListView.setOnItemClickListener((parent, view, position, id) -> {
            if (alarmListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
                if (toggle.setViewSelected(position)) {
                    selectedAlarms.add(position);
                } else {
                    selectedAlarms.remove(position);
                }

                //TODO zmiana widoku -> przycisku anuluj i usuń
                //TODO Zaznacz wszystkie i ilość zaznaczonych
            } else {
                Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("type", AddAlarmPresenter.Type.UPDATE);
                bundle.putLong("alarm_id", alarms.get(position).id);
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

  /*  @Override
    protected void onRestart() {
        super.onRestart();
        List<Alarm> alarms = AlarmDataBase.getAllAlarms();

        adapter = new AlarmListAdapter(this, alarms);

        alarmListView.setAdapter(adapter);
    }*/

    //TODO
    @Override
    public void showAlarmList(List<Alarm> alarms) {

    }

    //TODO ?? to samo co showAlarmList??
    @Override
    public void updateAlarmList() {

    }

    //TODO
    @Override
    public void showAlarmListForDeletion() {

    }

    //TODO
    @Override
    public void showMainAlarmList() {

    }
}