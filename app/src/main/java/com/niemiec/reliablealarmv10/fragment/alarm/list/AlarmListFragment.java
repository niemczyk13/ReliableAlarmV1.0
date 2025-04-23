package com.niemiec.reliablealarmv10.fragment.alarm.list;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.globals.enums.AddSingleAlarmType;
import com.example.globals.enums.AlarmListType;
import com.example.globals.enums.BundleNames;
import com.example.globals.enums.IsClickable;
import com.example.globals.enums.TypeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.fragment.alarm.list.dialog.CreateNewGroupAlarmDialog;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlarmMenuHandler menuHandler = new AlarmMenuHandler(this, viewHelper);
        viewHelper.setAlarmMenuHandler(menuHandler);
        requireActivity().addMenuProvider(menuHandler, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        // 2) Obsługa insets dla edge-to-edge
        View root = view; // fragment root
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ListView alarmListView = view.findViewById(R.id.alarm_list_view);

        // Zachowaj oryginalne paddingi
        final int tbLeft = toolbar.getPaddingLeft();
        final int tbRight = toolbar.getPaddingRight();
        final int tbBottom = toolbar.getPaddingBottom();

        final int lvLeft = alarmListView.getPaddingLeft();
        final int lvRight = alarmListView.getPaddingRight();
        final int lvBottom = alarmListView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 3) Toolbar: tylko górny padding = status bar
            toolbar.setPadding(tbLeft, sys.top, tbRight, tbBottom);
            // 4) ListView: top = status bar + wysokość toolbar, bottom = nav bar
            int toolbarHeight = toolbar.getHeight();
            alarmListView.setPadding(
                    lvLeft,
                    sys.top + toolbarHeight,
                    lvRight,
                    lvBottom + sys.bottom
            );
            // Nie konsumujemy, żeby dzieci też dostały insets, jeśli potrzebują
            return insets;
        });
        ViewCompat.requestApplyInsets(root);
    }

    private void createAlarmListPresenter() {
        assert getArguments() != null;
        AlarmListType alarmListType = (AlarmListType) getArguments().getSerializable(AlarmListType.ALARM_LIST_TYPE.name());
        presenter = new AlarmListPresenter(requireContext(), alarmListType);
        presenter.attach(this);
    }

    @Override
    public void onStart() {
        super.onStart();;
        presenter.initView();
        viewHelper.setClickableOnAlarmListView(IsClickable.CLICKABLE);
        isAddNewAlarmButtonIsClicked = false;
    }

    @Override
    public long getGroupAlarmId() {
        assert getArguments() != null;
        return getArguments().getLong(BundleNames.GROUP_ALARM_ID.name());
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
    public void showUpdateGroupAlarmDialog(GroupAlarmModel groupAlarm) {
        CreateNewGroupAlarmDialog dialog = new CreateNewGroupAlarmDialog(this, requireContext(), TypeView.UPDATE, groupAlarm);
        dialog.show();
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
    public void setAppTitleInActionBar(String title) {
        viewHelper.setAppTitleInActionBar(title);
    }

    @Override
    public void showEditButtonInActionBar() {
        viewHelper.showEditButtonInActionBar();
    }

    @Override
    public void refreshTitleInActionBar() {
        presenter.refreshTitleInActionBar();
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
    public boolean onEditButtonClick(CharSequence objectName) {
        String title = requireActivity().getString(R.string.edit_button_title);
        if (Objects.equals(objectName, title)) {
            presenter.onEditButtonClick();
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