package com.niemiec.reliablealarmv10.fragment.alarm.list.helper;

import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuProvider;

import com.niemiec.reliablealarmv10.R;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmMenuHandler implements MenuProvider {
    private final AlarmMenuListener fragment;
    private final AlarmListViewHelper viewHelper;


    public AlarmMenuHandler(AlarmMenuListener fragment, AlarmListViewHelper viewHelper) {
        this.fragment = fragment;
        this.viewHelper = viewHelper;
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {
        MenuItem trashIcon = menu.findItem(R.id.bin_image_button);
        boolean isAddAlarmVisible = isAddAlarmLayoutVisible();

        trashIcon.setVisible(!isAddAlarmVisible);
        viewHelper.setActionBarColor(isAddAlarmVisible ? R.color.blue_darker : R.color.blue);
    }

    private boolean isAddAlarmLayoutVisible() {
        LinearLayout addAlarmLayout = (LinearLayout) fragment.getViewById(R.id.add_single_or_group_alarm_linear_layout);
        return addAlarmLayout.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main_activity_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return fragment.onBinButtonClick(menuItem.getTitle());
    }

    public interface AlarmMenuListener {
        Context getContext();
        View getViewById(int id);
        boolean onBinButtonClick(CharSequence objectName);
    }
}
