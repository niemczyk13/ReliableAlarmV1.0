package com.niemiec.reliablealarmv10.fragment.alarm.list.helper;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.globals.enums.IsClickable;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListFragment;
import com.niemiec.reliablealarmv10.fragment.alarm.list.dialog.CreateNewGroupAlarmDialog;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.AlarmListListener;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.AlarmListAdapter;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.AlarmsList;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmListViewHelper {
    private final Fragment fragment;
    private AlarmListViewManagements viewManagements;
    private AlarmListAdapter adapter;
    private AlarmMenuHandler menuHandler;

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

    public void setAppTitleInActionBar(String title) {
        if (fragment.requireActivity() instanceof AppCompatActivity activity) {
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(title);
            }
        }
    }

    public void changeVisibility(View view, int visibility) {
        view.setVisibility(visibility);
    }

    public void changeTheVisibilityOfBrowsingViewItems(int visibility) {
        viewManagements.addNewAlarmButton.setVisibility(visibility);
        setTheAbilityToSelectListItems(viewManagements.alarmListView, visibility);
    }

    private void setTheAbilityToSelectListItems(ListView alarmListView, int visibility) {
        if (visibility == View.VISIBLE) {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        } else {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        }
    }

    public void showFullScreenMask() {
        viewManagements.mask.setVisibility(View.VISIBLE);
    }

    public void hideFullScreenMask() {
        viewManagements.mask.setVisibility(View.GONE);
    }

    public void initView(View view, AlarmListFragment alarmListFragment) {
        viewManagements = new AlarmListViewManagements(view, alarmListFragment);
    }

    public void setOnMaskClick(View.OnClickListener onClickListener) {
        viewManagements.mask.setOnClickListener(onClickListener);
    }

    public void setOnCancelDeleteButtonClick(View.OnClickListener onClickListener) {
        viewManagements.cancelDeleteAlarmButton.setOnClickListener(onClickListener);
    }

    public void setOnAlarmListItemClick(ListView.OnItemClickListener onItemClickListener) {
        viewManagements.alarmListView.setOnItemClickListener(onItemClickListener);
    }

    public void setOnAddNewAlarmButtonClick(View.OnClickListener onClickListener) {
        viewManagements.addNewAlarmButton.setOnClickListener(onClickListener);
    }

    public void setOnAddGroupAlarmButtonClick(View.OnClickListener onClickListener) {
        viewManagements.addGroupAlarmButton.setOnClickListener(onClickListener);
    }

    public void setOnAddSingleAlarmButtonClick(View.OnClickListener onClickListener) {
        viewManagements.addSingleAlarmButton.setOnClickListener(onClickListener);
    }

    public void setOnDeleteButtonClick(View.OnClickListener onClickListener) {
        viewManagements.deleteAlarmButton.setOnClickListener(onClickListener);
    }

    public void setClickableOnAlarmListView(IsClickable isClickable) {
        viewManagements.alarmListView.setClickable(isClickable.value());
    }

    public void showCreateNewGroupAlarmDialog() {
        viewManagements.dialog.show();
    }

    public boolean areAddSingleAndGroupAlarmButtonsVisible() {
        return viewManagements.addSingleOrGroupAlarm.getVisibility() == View.VISIBLE;
    }

    public boolean isAddGroupAlarmDialogShow() {
        return viewManagements.dialog.isShowing();
    }

    public void createAlarmList(List<Alarm> alarms, AlarmListListener listener) {
        adapter = new AlarmListAdapter(fragment.requireContext(), new AlarmsList(alarms), listener);
        viewManagements.alarmListView.setAdapter(adapter);
    }

    public void showAlarmList() {
        adapter.showMainList();
    }

    public void showDeleteAlarmList() {
        adapter.showDeleteList();
    }

    public void checkOnUncheckAlarmOnAlarmList(int positionOnList) {
        adapter.checkOnUncheckAlarm(positionOnList);
    }

    public List<Alarm> getSelectedAlarmsFromAlarmList() {
        return adapter.getSelectedAlarms();
    }

    public Alarm getAlarmFromAlarmList(int position) {
        return adapter.getAlarm(position);
    }

    public View getSingleOrGroupAlarmButtonsView() {
        return viewManagements.addSingleOrGroupAlarm;
    }

    public View getCancelOrDeleteButtonsView() {
        return viewManagements.cancelOrDelete;
    }

    public void showEditButtonInActionBar() {
        menuHandler.showEditButton();
    }

    public void setAlarmMenuHandler(AlarmMenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    public void showUpdateGroupAlarmDialog(CreateNewGroupAlarmDialog groupAlarm) {

    }
}
