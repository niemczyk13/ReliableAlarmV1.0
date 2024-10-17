package com.niemiec.reliablealarmv10.activity.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListFragment;
import com.niemiec.reliablealarmv10.utilities.enums.AlarmListType;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements MainContractMVP.View {
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMainPresenter();
        setTitle(R.string.title);
        setViews();
        setAlarmListFragment(savedInstanceState);
    }

    private void setAlarmListFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            AlarmListFragment alarmListFragment = AlarmListFragment.newInstance(AlarmListType.WITH_GROUP_ALARM);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, alarmListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem trashIcon = menu.findItem(R.id.bin_image_button);
        LinearLayout addAlarmLayout = findViewById(R.id.add_single_or_group_alarm_linear_layout);

        if (addAlarmLayout.getVisibility() == View.VISIBLE) {
            trashIcon.setVisible(false);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_darker)));
            }
        } else {
            trashIcon.setVisible(true);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void createMainPresenter() {
        presenter = new MainPresenter(getApplicationContext());
        presenter.attach(this);
    }

    private void setViews() {
        presenter.initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.initView();
    }


    @Override
    public void showActivity(List<SingleAlarmEntity> singleAlarms) {
        changeDefaultAppSettings();
    }

    private void changeDefaultAppSettings() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }
}