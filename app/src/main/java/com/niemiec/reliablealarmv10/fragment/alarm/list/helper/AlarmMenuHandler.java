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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import com.niemiec.reliablealarmv10.R;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmMenuHandler implements MenuProvider {
    private final AlarmMenuListener fragment;
    private final Fragment fr;
    private final AlarmListViewHelper viewHelper;
    private boolean editButtonVisible = false;
    private MenuItem binButton;
    private String title;


    public AlarmMenuHandler(AlarmMenuListener fragment, AlarmListViewHelper viewHelper) {
        this.fragment = fragment;
        this.fr = (Fragment) fragment;
        this.viewHelper = viewHelper;
        this.title = fr.getContext().getString(R.string.title);
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {
        MenuItem binButton = menu.findItem(R.id.bin_image_button);
        boolean isAddAlarmVisible = isAddAlarmLayoutVisible();
        binButton.setVisible(!isAddAlarmVisible);
        viewHelper.setActionBarColor(isAddAlarmVisible ? R.color.blue_darker : R.color.main_blue);
    }

    private boolean isAddAlarmLayoutVisible() {
        LinearLayout addAlarmLayout = (LinearLayout) fragment.getViewById(R.id.add_single_or_group_alarm_linear_layout);
        return addAlarmLayout.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        binButton = menu.findItem(R.id.bin_image_button);
        MenuItem editButton = menu.findItem(R.id.edit_image_button);
        editButton.setVisible(editButtonVisible);
        setTitle(title);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (Objects.equals(menuItem.getTitle(), binButton.getTitle())) {
            return fragment.onBinButtonClick(menuItem.getTitle());
        } else {
            return fragment.onEditButtonClick(menuItem.getTitle());
        }
    }

    public void showEditButton() {
        editButtonVisible = true;
    }

    public void setTitle(String title) {
        this.title = title;
        if (fr.requireActivity() instanceof AppCompatActivity activity) {
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(title);
            }
        }
    }

    public interface AlarmMenuListener {
        Context getContext();
        View getViewById(int id);
        boolean onBinButtonClick(CharSequence objectName);
        boolean onEditButtonClick(CharSequence title);
    }
}
