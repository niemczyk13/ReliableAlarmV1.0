package com.niemiec.reliablealarmv10.fragment.alarm.list.helper;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AlarmListViewHelper {
    private Fragment fragment;

    public AlarmListViewHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setActionBarColor(int colorResId) {
        if (fragment.requireActivity() instanceof AppCompatActivity activity) {
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setBackgroundDrawable(
                        new ColorDrawable(fragment.getResources().getColor(colorResId))
                );
            }
        }
    }

    public void changeVisibility(View view, int visibility) {
        view.setVisibility(visibility);
    }

    public void changeTheVisibilityOfBrowsingViewItems(View addNewAlarmButton, ListView alarmListView, int visibility) {
        addNewAlarmButton.setVisibility(visibility);
        setTheAbilityToSelectListItems(alarmListView, visibility);
    }

    private void setTheAbilityToSelectListItems(ListView alarmListView, int visibility) {
        if (visibility == View.VISIBLE) {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        } else {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        }
    }

    public void showFullScreenMask(FrameLayout mask) {
        mask.setVisibility(View.VISIBLE);
    }

    public void hideFullScreenMask(FrameLayout mask) {
        mask.setVisibility(View.GONE);
    }
}
