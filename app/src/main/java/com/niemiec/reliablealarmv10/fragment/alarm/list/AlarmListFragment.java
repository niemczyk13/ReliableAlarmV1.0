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
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.AlarmListListener;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.AlarmListAdapter;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data.AlarmsList;
import com.niemiec.reliablealarmv10.activity.main.dialog.CreateNewGroupAlarmDialog;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.utilities.enums.AlarmListType;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmListFragment extends Fragment implements AlarmListContractMVP.View, AlarmListListener {
    private static final String ARG_ALARM_LIST_TYPE = "alarmListType";
    private AlarmListType alarmListType;
    private AlarmListPresenter presenter;
    private AlarmListAdapter adapter;

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

    public static AlarmListFragment newInstance(AlarmListType alarmListType) {
        AlarmListFragment fragment = new AlarmListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ALARM_LIST_TYPE, alarmListType);
        fragment.setArguments(args);
        return fragment;
    }

    private void createAlarmListPresenter() {
        presenter = new AlarmListPresenter(this.getContext(), alarmListType);
        presenter.attach(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            alarmListType = (AlarmListType) getArguments().getSerializable(ARG_ALARM_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        createAlarmListPresenter();

        initView(view);
        setListeners();
        setViews();

        if (alarmListType == AlarmListType.WITH_GROUP_ALARM) {

        } else {

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_activity_menu, menu);  // Inflatuj menu fragmentu
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        presenter.onBinButtonClick();
                        return false;
                    default:
                        return false;
                }
            }

        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);  // Rejestracja menu
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.initView();
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
    }

    private void setListeners() {
        mask.setOnClickListener(view -> {
            //presenter.onFullScreenMaskViewClick();
        });

        cancelDeleteAlarmButton.setOnClickListener(view -> {
            presenter.onCancelButtonClick();
            alarmListView.setClickable(false);       });

        alarmListView.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onAlarmListItemClick(position);
            alarmListView.setClickable(false);
        });

        addNewAlarmButton.setOnClickListener(view -> {
            presenter.onAddNewAlarmButtonClick();
        });

        addGroupAlarmButton.setOnClickListener(view -> {
            presenter.onCreateGroupAlarmButtonClick();

        });

        addSingleAlarmButton.setOnClickListener(view -> {
            if (!isAddNewAlarmButtonIsClicked) {
                presenter.onCreateAlarmButtonClick();
                isAddNewAlarmButtonIsClicked = true;
            }
        });

        deleteAlarmButton.setOnClickListener(view -> presenter.onDeleteButtonClick(adapter.getSelectedAlarms()));
    }

    private void setViews() {
        presenter.initView();
    }

    @Override
    public void showFragment(List<SingleAlarmEntity> singleAlarms) {
        changeTheVisibilityOfDeleteViewItems(View.GONE);
        changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        createAlarmListWithAdapter(singleAlarms);
        adapter.showMainList();
    }

    private void changeTheVisibilityOfDeleteViewItems(int visibility) {
        cancelOrDelete.setVisibility(visibility);
    }

    private void changeTheVisibilityOfBrowsingViewItems(int visibility) {
        addNewAlarmButton.setVisibility(visibility);
        setTheAbilityToSelectListItems(visibility);
    }

    private void changeTheVisibilityOfAddAlarmButtonsViewItems(int visibility) {
        addSingleOrGroupAlarm.setVisibility(visibility);
    }

    private void setTheAbilityToSelectListItems(int visibility) {
        if (visibility == View.VISIBLE) {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        } else {
            alarmListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        }
    }

    private void createAlarmListWithAdapter(List<SingleAlarmEntity> singleAlarms) {
        adapter = new AlarmListAdapter(this.getContext(), new AlarmsList(singleAlarms), this);
        alarmListView.setAdapter(adapter);
    }

    @Override
    public void showAlarmListForDeletion() {
        changeTheVisibilityOfDeleteViewItems(View.VISIBLE);
        changeTheVisibilityOfBrowsingViewItems(View.GONE);
        adapter.showDeleteList();

    }

    @Override
    public void showNormalView() {
        changeTheVisibilityOfDeleteViewItems(View.GONE);
        changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        changeTheVisibilityOfAddAlarmButtonsViewItems(View.GONE);
        adapter.showMainList();
    }

    @Override
    public void updateAlarmList(List<SingleAlarmEntity> singleAlarms) {
        createAlarmListWithAdapter(singleAlarms);
    }

    @Override
    public void showCreateNewAlarmActivity() {
        Intent intent = new Intent(this.getContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.CREATE);
        intent.putExtra("data", bundle);

        startActivity(intent);
    }

    @Override
    public void showUpdateAlarmActivity(int position) {
        Intent intent = new Intent(this.getContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.UPDATE);
        bundle.putLong("alarm_id", adapter.getAlarm(position).id);
        intent.putExtra("data", bundle);
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
        CreateNewGroupAlarmDialog dialog = new CreateNewGroupAlarmDialog(this,this.getContext());
        dialog.show();
    }

    @Override
    public void showAddSingleAndGroupAlarmButtons() {
        changeTheVisibilityOfAddAlarmButtonsViewItems(View.VISIBLE);
    }

    @Override
    public boolean areAddSingleAndGroupAlarmButtonsVisible() {
        return addSingleOrGroupAlarm.getVisibility() == View.VISIBLE;
    }

    @Override
    public void hideAddSingleAndGroupAlarmButtons() {
        changeTheVisibilityOfAddAlarmButtonsViewItems(View.GONE);
    }

    @Override
    public void showFullScreenMask() {
        mask.setVisibility(View.VISIBLE);
        //invalidateOptionsMenu();
    }

    @Override
    public void hideFullScreenMask() {
        mask.setVisibility(View.GONE);
        //invalidateOptionsMenu();
    }

    @Override
    public void switchOnOffClick(long id) {
        presenter.onSwitchOnOffAlarmClick(id);
    }
}