package com.niemiec.reliablealarmv10.activity.alarm.group;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.globals.enums.AlarmListType;
import com.example.globals.enums.BundleNames;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListFragment;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class GroupAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_alarm);
        setAlarmListFragment(savedInstanceState, getGroupAlarmId());
    }

    private long getGroupAlarmId() {
        Bundle bundle = getIntent().getBundleExtra(BundleNames.DATA.name());
        assert bundle != null;
        return bundle.getLong(BundleNames.GROUP_ALARM_ID.name());
    }


    private void setAlarmListFragment(Bundle savedInstanceState, long groupAlarmId) {
        if (savedInstanceState == null) {
            AlarmListFragment alarmListFragment = AlarmListFragment.newInstanceForGroupAlarmActivity(AlarmListType.WITHOUT_GROUP_ALARM, groupAlarmId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, alarmListFragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeDefaultAppSettings();
    }

    private void changeDefaultAppSettings() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }
}