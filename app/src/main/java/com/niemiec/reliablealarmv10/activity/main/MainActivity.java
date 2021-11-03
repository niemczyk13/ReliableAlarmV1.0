package com.niemiec.reliablealarmv10.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContractMVP.View {
    private MainPresenter presenter;

    private FloatingActionButton addNewAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);

        createMainPresenter();

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
        bundle.putString("type", "create");
        intent.putExtra("data", bundle);

        startActivity(intent);
    }

    private void createMainPresenter() {
        presenter = new MainPresenter(getApplicationContext());
        presenter.attach(this);
    }

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