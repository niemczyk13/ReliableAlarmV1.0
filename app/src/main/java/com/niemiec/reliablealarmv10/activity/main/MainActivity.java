package com.niemiec.reliablealarmv10.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.R;

public class MainActivity extends AppCompatActivity implements MainContractMVP.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //stworzenie bazy danych
        //dodanie do bazy danych do alarmu podstawowego

    }

    //TODO
    @Override
    public void showAlarmList() {

    }

    //TODO
    @Override
    public void updateAlarmList() {

    }

    //TODO
    @Override
    public void showAlarmListForDeletion() {

    }
}