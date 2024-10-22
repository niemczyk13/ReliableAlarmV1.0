package com.niemiec.reliablealarmv10.fragment.alarm.list;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.globals.enums.AlarmListType;
import com.example.globals.enums.BundleNames;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.activity.alarm.group.GroupAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.AlarmListListener;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.adapter.AlarmListAdapter;
import com.niemiec.reliablealarmv10.fragment.alarm.list.list.adapter.data.SingleAlarmsList;
import com.niemiec.reliablealarmv10.fragment.alarm.list.dialog.CreateNewGroupAlarmDialog;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.fragment.alarm.list.helper.AlarmListViewHelper;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmListFragment extends Fragment implements AlarmListContractMVP.View, AlarmListListener {
    private static final String ARG_ALARM_LIST_TYPE = "alarmListType";
    private AlarmListViewHelper viewHelper;
    private AlarmListType alarmListType;
    private AlarmListPresenter presenter;
    private AlarmListAdapter adapter;
    CreateNewGroupAlarmDialog dialog;

    private ListView alarmListView;
    private FrameLayout mask;
    private FloatingActionButton addNewAlarmButton;
    private LinearLayout addSingleOrGroupAlarm;
    private MaterialButton addSingleAlarmButton;
    private MaterialButton addGroupAlarmButton;
    private LinearLayout cancelOrDelete;
    private Button cancelDeleteAlarmButton;
    private Button deleteAlarmButton;
    private boolean isAddNewAlarmButtonIsClicked = false;

    public AlarmListFragment() {
    }

    public static AlarmListFragment newInstance(AlarmListType alarmListType, long groupAlarmId) {
        AlarmListFragment fragment = new AlarmListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ALARM_LIST_TYPE, alarmListType);
        args.putLong(BundleNames.GROUP_ALARM_ID.name(), groupAlarmId);
        fragment.setArguments(args);
        return fragment;
    }

    private void createAlarmListPresenter() {
        presenter = new AlarmListPresenter(requireContext(), alarmListType);
        presenter.attach(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alarmListType = (AlarmListType) getArguments().getSerializable(ARG_ALARM_LIST_TYPE);
        }
        viewHelper = new AlarmListViewHelper(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        createAlarmListPresenter();
        initView(view);
        setListeners();
        setViews();

        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM) {
            //TODO sprawdzeniue czy istnieje w bazie, jak nie to komunikat
        } else {
            //TODO
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerMenu();
    }

    private void registerMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_activity_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return handleMenuSelection(menuItem);
            }

            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                prepareMenu(menu);
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private boolean handleMenuSelection(@NonNull MenuItem menuItem) {
        String title = requireActivity().getString(R.string.bin_button_title);
        if (Objects.equals(menuItem.getTitle(), title)) {
            presenter.onBinButtonClick();
            return true;
        }
        return false;
    }

    private void prepareMenu(@NonNull Menu menu) {
        MenuItem trashIcon = menu.findItem(R.id.bin_image_button);
        boolean isAddAlarmVisible = isAddAlarmLayoutVisible();

        trashIcon.setVisible(!isAddAlarmVisible);
        viewHelper.setActionBarColor(isAddAlarmVisible ? R.color.blue_darker : R.color.blue);
    }

    private boolean isAddAlarmLayoutVisible() {
        LinearLayout addAlarmLayout = requireView().findViewById(R.id.add_single_or_group_alarm_linear_layout);
        return addAlarmLayout.getVisibility() == View.VISIBLE;
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
        alarmListView.setClickable(true);
        isAddNewAlarmButtonIsClicked = false;
    }

    private void initView(View view) {
        alarmListView = view.findViewById(R.id.alarm_list_view);
        mask = view.findViewById(R.id.full_screen_mask);
        addNewAlarmButton = view.findViewById(R.id.add_alarm_button);
        addSingleOrGroupAlarm = view.findViewById(R.id.add_single_or_group_alarm_linear_layout);
        addSingleAlarmButton = view.findViewById(R.id.add_single_alarm_button);
        addGroupAlarmButton = view.findViewById(R.id.add_group_alarm_button);
        cancelOrDelete = view.findViewById(R.id.cancel_or_delete_linear_layout);
        cancelDeleteAlarmButton = view.findViewById(R.id.cancel_delete_alarm_button);
        deleteAlarmButton = view.findViewById(R.id.delete_alarm_button);
        dialog = new CreateNewGroupAlarmDialog(this, requireContext());
    }

    private void setListeners() {
        mask.setOnClickListener(view -> presenter.onFullScreenMaskViewClick());

        cancelDeleteAlarmButton.setOnClickListener(view -> {
            presenter.onCancelButtonClick();
            alarmListView.setClickable(false);
        });

        alarmListView.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onAlarmListItemClick(position);
            alarmListView.setClickable(false);
        });

        addNewAlarmButton.setOnClickListener(view -> presenter.onAddNewAlarmButtonClick());

        addGroupAlarmButton.setOnClickListener(view -> presenter.onCreateGroupAlarmButtonClick());

        addSingleAlarmButton.setOnClickListener(view -> {
            if (!isAddNewAlarmButtonIsClicked) {
                presenter.onCreateAlarmButtonClick();
                isAddNewAlarmButtonIsClicked = true;
            }
        });

        deleteAlarmButton.setOnClickListener(view -> presenter.onDeleteButtonClick(adapter.getSelectedAlarms()));
    }

    private void setViews() {
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM) {
            assert getArguments() != null;
            presenter.initViewForGroupAlarm(getArguments().getLong(BundleNames.GROUP_ALARM_ID.name()));
        }
        else {
        }
    }

    @Override
    public void showFragment(List<SingleAlarmEntity> singleAlarms) {
        viewHelper.changeVisibility(cancelOrDelete, View.GONE);
        viewHelper.changeTheVisibilityOfBrowsingViewItems(addNewAlarmButton, alarmListView, View.VISIBLE);
        createSingleAlarmListWithAdapter(singleAlarms);
        adapter.showMainList();
    }

    private void createSingleAlarmListWithAdapter(List<SingleAlarmEntity> singleAlarms) {
        adapter = new AlarmListAdapter(this.getContext(), new SingleAlarmsList(singleAlarms), this);
        alarmListView.setAdapter(adapter);
    }

    @Override
    public void showFragment(List<GroupAlarmModel> groupAlarms, List<SingleAlarmModel> singleAlarms) {

    }

    @Override
    public void showAlarmListForDeletion() {
        viewHelper.changeVisibility(cancelOrDelete, View.VISIBLE);
        viewHelper.changeTheVisibilityOfBrowsingViewItems(addNewAlarmButton, alarmListView, View.GONE);
        adapter.showDeleteList();

    }

    @Override
    public void showNormalView() {
        viewHelper.changeVisibility(cancelOrDelete, View.GONE);
        viewHelper.changeTheVisibilityOfBrowsingViewItems(addNewAlarmButton, alarmListView, View.VISIBLE);
        viewHelper.changeVisibility(addSingleOrGroupAlarm, View.GONE);
        adapter.showMainList();
    }

    @Override
    public void updateAlarmList(List<SingleAlarmEntity> singleAlarms) {
        createSingleAlarmListWithAdapter(singleAlarms);
    }

    @Override
    public void updateAlarmListForSingleAlarmModel(List<SingleAlarmModel> singleAlarms) {

    }

    @Override
    public void showCreateNewAlarmActivity() {
        Intent intent = new Intent(this.getContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleNames.TYPE.name(), AddAlarmPresenter.Type.CREATE);
        intent.putExtra(BundleNames.DATA.name(), bundle);

        startActivity(intent);
    }

    @Override
    public void showUpdateAlarmActivity(int position) {
        Intent intent = new Intent(this.getContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleNames.TYPE.name(), AddAlarmPresenter.Type.UPDATE);
        bundle.putLong(BundleNames.ALARM_ID.name(), adapter.getAlarm(position).id);
        intent.putExtra(BundleNames.DATA.name(), bundle);
        startActivity(intent);
    }

    @Override
    public void showGroupAlarmActivity(long groupAlarmId) {
        Intent intent = new Intent(getContext(), GroupAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(BundleNames.GROUP_ALARM_ID.name(), groupAlarmId);
        intent.putExtra(BundleNames.DATA.name(), bundle);
        startActivity(intent);
    }

    @Override
    public void checkOrUncheckAlarm(int position) {
        adapter.checkOnUncheckAlarm(position);
    }

    @Override
    public void startAlarm(SingleAlarmEntity singleAlarm) {
        AlarmManagerManagement.startAlarm(singleAlarm, this.getContext());
    }

    @Override
    public void stopAlarm(SingleAlarmEntity singleAlarm) {
        AlarmManagerManagement.stopAlarm(singleAlarm, this.getContext());
    }

    @Override
    public void updateNotification(List<SingleAlarmEntity> activeSingleAlarms) {
        AlarmNotificationManager.updateNotification(this.getContext(), activeSingleAlarms);
    }

    @Override
    public void showCreateNewAlarmDialog() {
        dialog.show();
    }

    @Override
    public void showAddSingleAndGroupAlarmButtons() {
        viewHelper.changeVisibility(addSingleOrGroupAlarm, View.VISIBLE);
    }

    @Override
    public boolean areAddSingleAndGroupAlarmButtonsVisible() {
        return addSingleOrGroupAlarm.getVisibility() == View.VISIBLE;
    }

    @Override
    public void hideAddSingleAndGroupAlarmButtons() {
        viewHelper.changeVisibility(addSingleOrGroupAlarm, View.GONE);
    }

    @Override
    public void showFullScreenMask() {
        viewHelper.showFullScreenMask(mask);
        requireActivity().invalidateMenu();
    }

    @Override
    public void hideFullScreenMask() {
        viewHelper.hideFullScreenMask(mask);
        requireActivity().invalidateMenu();
    }

    @Override
    public boolean isAddGroupAlarmDialogShow() {
        return dialog.isShowing();
    }

    @Override
    public void switchOnOffClick(long id) {
        presenter.onSwitchOnOffAlarmClick(id);
    }
}