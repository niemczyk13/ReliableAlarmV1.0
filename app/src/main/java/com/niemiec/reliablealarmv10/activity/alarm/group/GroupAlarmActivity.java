package com.niemiec.reliablealarmv10.activity.alarm.group;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.globals.enums.AlarmListType;
import com.example.globals.enums.BundleNames;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListFragment;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GroupAlarmActivity extends AppCompatActivity implements GroupAlarmContractMVP.View {
    private GroupAlarmPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_alarm);


        //TODO pewnie id przekazać do fragmentu za pomocą bundle?
        Bundle bundle = getIntent().getBundleExtra(BundleNames.DATA.name());
        long id = bundle.getLong(BundleNames.GROUP_ALARM_ID.name());

        setAlarmListFragment(savedInstanceState, id);
    }

    private void setAlarmListFragment(Bundle savedInstanceState, long groupAlarmId) {
        if (savedInstanceState == null) {
            AlarmListFragment alarmListFragment = AlarmListFragment.newInstance(AlarmListType.WITHOUT_GROUP_ALARM, groupAlarmId);
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