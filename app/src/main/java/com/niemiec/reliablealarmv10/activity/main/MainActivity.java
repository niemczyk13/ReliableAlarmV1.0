package com.niemiec.reliablealarmv10.activity.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.AlarmListAdapter;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.AlarmListListener;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data.AlarmsList;
import com.niemiec.reliablealarmv10.activity.main.dialog.CreateNewGroupAlarmDialog;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements MainContractMVP.View, AlarmListListener {
    private MainPresenter presenter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMainPresenter();
        initView();
        setListeners();
        setViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        menu.getItem(0).setOnMenuItemClickListener(item -> {
            presenter.onBinButtonClick();
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem trashIcon = menu.findItem(R.id.bin_image_button);
        LinearLayout addAlarmLayout = findViewById(R.id.add_single_or_group_alarm_linear_layout);

        if (addAlarmLayout.getVisibility() == View.VISIBLE) {
            trashIcon.setVisible(false);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_darker)));
            }
        } else {
            trashIcon.setVisible(true);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void createMainPresenter() {
        presenter = new MainPresenter(getApplicationContext());
        presenter.attach(this);
    }

    private void initView() {
        setTitle(R.string.title);
        alarmListView = findViewById(R.id.alarm_list_view);
        mask = findViewById(R.id.full_screen_mask);
        addNewAlarmButton = findViewById(R.id.add_alarm_button);
        addSingleOrGroupAlarm = findViewById(R.id.add_single_or_group_alarm_linear_layout);
        addSingleAlarmButton = findViewById(R.id.add_single_alarm_button);
        addGroupAlarmButton = findViewById(R.id.add_group_alarm_button);
        cancelOrDelete = findViewById(R.id.cancel_or_delete_linear_layout);
        cancelDeleteAlarmButton = findViewById(R.id.cancel_delete_alarm_button);
        deleteAlarmButton = findViewById(R.id.delete_alarm_button);
    }

    private void setListeners() {
        mask.setOnClickListener(view -> {
            presenter.onFullScreenMaskViewClick();
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
    protected void onStart() {
        super.onStart();
        presenter.initView();
        alarmListView.setClickable(true);
        isAddNewAlarmButtonIsClicked = false;
    }


    @Override
    public void showActivity(List<SingleAlarmEntity> singleAlarms) {
        changeDefaultAppSettings();
        changeTheVisibilityOfDeleteViewItems(View.GONE);
        changeTheVisibilityOfBrowsingViewItems(View.VISIBLE);
        createAlarmListWithAdapter(singleAlarms);
        adapter.showMainList();
    }

    private void changeDefaultAppSettings() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
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
        adapter = new AlarmListAdapter(this, new AlarmsList(singleAlarms), this);
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
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", AddAlarmPresenter.Type.CREATE);
        intent.putExtra("data", bundle);

        startActivity(intent);
    }

    @Override
    public void showUpdateAlarmActivity(int position) {
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
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
        AlarmManagerManagement.startAlarm(singleAlarm, getApplicationContext());
    }

    @Override
    public void stopAlarm(SingleAlarmEntity singleAlarm) {
        AlarmManagerManagement.stopAlarm(singleAlarm, getApplicationContext());
    }

    @Override
    public void updateNotification(List<SingleAlarmEntity> activeSingleAlarms) {
        AlarmNotificationManager.updateNotification(getApplicationContext(), activeSingleAlarms);
    }

    @Override
    public void showCreateNewAlarmDialog() {
        CreateNewGroupAlarmDialog dialog = new CreateNewGroupAlarmDialog(this,this);
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
        invalidateOptionsMenu();
    }

    @Override
    public void hideFullScreenMask() {
        mask.setVisibility(View.GONE);
        invalidateOptionsMenu();
    }


    @Override
    public void switchOnOffClick(long id) {
        presenter.onSwitchOnOffAlarmClick(id);
    }
}