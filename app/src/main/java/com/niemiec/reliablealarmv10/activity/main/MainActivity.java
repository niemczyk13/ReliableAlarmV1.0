package com.niemiec.reliablealarmv10.activity.main;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.globals.enums.AlarmListType;
import com.example.globals.themes.ThemeManager;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListFragment;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarmListFragment(savedInstanceState);
        EdgeToEdge.enable(this);
        ThemeManager.applySavedTheme(this);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // albo dla jasnego: AppCompatDelegate.MODE_NIGHT_NO
        // albo automatycznie: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        //WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
    }

    private void setAlarmListFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            AlarmListFragment alarmListFragment = AlarmListFragment.newInstanceForMainActivity(AlarmListType.WITH_GROUP_ALARM);
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
        //getWindow().setStatusBarColor(Color.BLACK);
    }
}