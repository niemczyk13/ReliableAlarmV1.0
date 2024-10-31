package com.niemiec.reliablealarmv10.fragment.alarm.list;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globals.enums.AddSingleAlarmType;
import com.example.globals.enums.AlarmListType;
import com.example.globals.enums.BundleNames;
import com.example.globals.enums.IsClickable;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.fragment.alarm.list.helper.AlarmActivityNavigationHelper;
import com.niemiec.reliablealarmv10.fragment.alarm.list.helper.AlarmListListenerHelper;
import com.niemiec.reliablealarmv10.fragment.alarm.list.helper.AlarmMenuHandler;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.AlarmListListener;
import com.niemiec.reliablealarmv10.fragment.alarm.list.helper.AlarmListViewHelper;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmListFragment extends Fragment implements AlarmListContractMVP.View, AlarmListListener, AlarmMenuHandler.AlarmMenuListener, AlarmListListenerHelper.AlarmListActionListener {
    private AlarmListViewHelper viewHelper;
    private AlarmListType alarmListType;
    private AlarmListPresenter presenter;
    private AlarmActivityNavigationHelper activationHelper;
    private AlarmListListenerHelper listenerHelper;
    private boolean isAddNewAlarmButtonIsClicked = false;

    public AlarmListFragment() {
    }

    public static AlarmListFragment newInstanceForGroupAlarmActivity(AlarmListType alarmListType, long groupAlarmId) {
        return createFragment(alarmListType, groupAlarmId);
    }

    public static AlarmListFragment newInstanceForMainActivity(AlarmListType alarmListType) {
        return createFragment(alarmListType, null);
    }

    private static AlarmListFragment createFragment(AlarmListType alarmListType, Long groupAlarmId) {
        AlarmListFragment fragment = new AlarmListFragment();
        Bundle args = new Bundle();
        args.putSerializable(AlarmListType.ALARM_LIST_TYPE.name(), alarmListType);
        if (AlarmListType.WITHOUT_GROUP_ALARM == alarmListType && groupAlarmId != null)
            args.putLong(BundleNames.GROUP_ALARM_ID.name(), groupAlarmId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alarmListType = (AlarmListType) getArguments().getSerializable(AlarmListType.ALARM_LIST_TYPE.name());
        }
        viewHelper = new AlarmListViewHelper(this);
        activationHelper = new AlarmActivityNavigationHelper(requireContext());
        listenerHelper = new AlarmListListenerHelper(viewHelper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        createAlarmListPresenter();
        viewHelper.initView(view, this);
        listenerHelper.setupListeners(this);

        return view;
    }

    private void createAlarmListPresenter() {
        presenter = new AlarmListPresenter(requireContext(), alarmListType);
        presenter.attach(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new AlarmMenuHandler(this, viewHelper), getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM) {
            assert getArguments() != null;
            presenter.initViewForGroupAlarm(getArguments().getLong(BundleNames.GROUP_ALARM_ID.name()));
        }
        else {
            presenter.initViewForAllAlarms();
        }
        viewHelper.setClickableOnAlarmListView(IsClickable.CLICKABLE);
        isAddNewAlarmButtonIsClicked = false;
    }

    @Override
    public void showFragment(List<Alarm> alarms) {
        viewHelper.changeVisibility(viewHelper.getCancelOrDeleteButtonsView(), View.GONE);
        viewHelper.changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        viewHelper.createAlarmList(alarms, this);
        viewHelper.showAlarmList();
    }

    @Override
    public void showAlarmListForDeletion() {
        viewHelper.changeVisibility(viewHelper.getCancelOrDeleteButtonsView(), View.VISIBLE);
        viewHelper.changeTheVisibilityOfBrowsingViewItems(View.GONE);
        viewHelper.showDeleteAlarmList();
    }

    @Override
    public void showNormalView() {
        viewHelper.changeVisibility(viewHelper.getCancelOrDeleteButtonsView(), View.GONE);
        viewHelper.changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        viewHelper.changeVisibility(viewHelper.getSingleOrGroupAlarmButtonsView(), View.GONE);
        viewHelper.showAlarmList();
    }

    @Override
    public void updateAlarmList(List<Alarm> alarms) {
        viewHelper.createAlarmList(alarms, this);
    }

    @Override
    public void showCreateNewAlarmActivity(AddSingleAlarmType addSingleAlarmType) {
        activationHelper.navigateToAddAlarmActivity(addSingleAlarmType);
    }

    @Override
    public void showCreateNewAlarmActivityForGroupAlarm(long groupAlarmId, AddSingleAlarmType addSingleAlarmType) {
        activationHelper.navigateToAddAlarmActivityForGroupAlarm(groupAlarmId, addSingleAlarmType);
    }

    @Override
    public void showUpdateAlarmActivity(SingleAlarmModel singleAlarmModel) {
        activationHelper.navigateToUpdateAlarmActivity(singleAlarmModel);
    }

    @Override
    public void showGroupAlarmActivity(GroupAlarmModel groupAlarmModel) {
        activationHelper.navigateToGroupAlarmActivity(groupAlarmModel);
    }

    @Override
    public void checkOrUncheckAlarm(int positionOnList) {
        viewHelper.checkOnUncheckAlarmOnAlarmList(positionOnList);
    }

    @Override
    public void updateNotification(List<SingleAlarmModel> activeSingleAlarms) {
        AlarmNotificationManager.updateNotification(this.getContext(), activeSingleAlarms);
    }

    @Override
    public void showCreateNewAlarmDialog() {
        viewHelper.showCreateNewGroupAlarmDialog();
    }

    @Override
    public void showAddSingleAndGroupAlarmButtons() {
        viewHelper.changeVisibility(viewHelper.getSingleOrGroupAlarmButtonsView(), View.VISIBLE);
    }

    @Override
    public boolean areAddSingleAndGroupAlarmButtonsVisible() {
        return viewHelper.areAddSingleAndGroupAlarmButtonsVisible();
    }

    @Override
    public void hideAddSingleAndGroupAlarmButtons() {
        viewHelper.changeVisibility(viewHelper.getSingleOrGroupAlarmButtonsView(), View.GONE);
    }

    @Override
    public void showFullScreenMask() {
        viewHelper.showFullScreenMask();
        requireActivity().invalidateMenu();
    }

    @Override
    public void hideFullScreenMask() {
        viewHelper.hideFullScreenMask();
        requireActivity().invalidateMenu();
    }

    @Override
    public boolean isAddGroupAlarmDialogShow() {
        return viewHelper.isAddGroupAlarmDialogShow();
    }

    @Override
    public void switchOnOffClick(Alarm alarm) {
        presenter.onSwitchOnOffAlarmClick(alarm);
    }

    @Override
    public View getViewById(int id) {
        return requireView().findViewById(id);
    }

    @Override
    public boolean onBinButtonClick(CharSequence objectName) {
        String title = requireActivity().getString(R.string.bin_button_title);
        if (Objects.equals(objectName, title)) {
            presenter.onBinButtonClick();
            return true;
        }
        return false;
    }

    @Override
    public void onFullScreenMaskClick() {
        presenter.onFullScreenMaskViewClick();
    }

    @Override
    public void onCancelDeleteButtonClick() {
        presenter.onCancelButtonClick();
        viewHelper.setClickableOnAlarmListView(IsClickable.NON_CLICKABLE);
    }

    @Override
    public void onAlarmListItemClick(Alarm alarm, int position) {
        presenter.onAlarmListItemClick(viewHelper.getAlarmFromAlarmList(position), position);
        viewHelper.setClickableOnAlarmListView(IsClickable.NON_CLICKABLE);
    }

    @Override
    public void onAddNewAlarmButtonClick() {
        presenter.onAddNewAlarmButtonClick();
    }

    @Override
    public void onCreateGroupAlarmButtonClick() {
        presenter.onCreateGroupAlarmButtonClick();
    }

    @Override
    public void onCreateSingleAlarmButtonClick() {
        if (!isAddNewAlarmButtonIsClicked) {
            presenter.onCreateAlarmButtonClick();
            isAddNewAlarmButtonIsClicked = true;
        }
    }

    @Override
    public void onDeleteButtonClick(List<Alarm> selectedAlarms) {
        presenter.onDeleteButtonClick(selectedAlarms);
    }
}