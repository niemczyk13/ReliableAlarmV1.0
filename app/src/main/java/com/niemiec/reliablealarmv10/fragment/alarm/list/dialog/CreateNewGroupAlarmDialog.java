package com.niemiec.reliablealarmv10.fragment.alarm.list.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.globals.enums.TypeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListContractMVP;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.utilities.keyboard.KeyboardUtilities;

public class CreateNewGroupAlarmDialog {
    private final AlarmListContractMVP.View mainActivityView;
    private final Dialog dialog;
    private Button cancelButton;
    private Button saveButton;
    private EditText nameEditText;
    private EditText noteEditText;
    private final TypeView typeView;
    private final Context context;
    private GroupAlarmModel groupAlarmModel;

    public CreateNewGroupAlarmDialog(AlarmListContractMVP.View mainActivityView, Context context, TypeView typeView) {
        this.mainActivityView = mainActivityView;
        this.context = context;
        this.typeView = typeView;
        dialog = new Dialog(context);
        setupDialogAppearance();
        dialog.setContentView(R.layout.add_group_alarm_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        initView();
        setListeners();
    }

    public CreateNewGroupAlarmDialog(AlarmListContractMVP.View mainActivityView, Context context, TypeView typeView, GroupAlarmModel groupAlarmModel) {
        this(mainActivityView, context, typeView);
        this.groupAlarmModel = groupAlarmModel;
        initViewForUpdateGroupAlarm();
    }

    private void setupDialogAppearance() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        if (window != null) {
            // Nie dodawaj FLAG_DIM_BEHIND, aby zapobiec przyciemnieniu tła
            window.setBackgroundDrawableResource(R.drawable.add_group_alarm_dialog_background);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            window.setAttributes(layoutParams);
        }
    }

    public void show() {
        dialog.show();
        nameEditText.requestFocus();
        nameEditText.postDelayed(() -> {
            KeyboardUtilities.showKeyboard(dialog.getContext(), nameEditText);
        }, 200);
    }

    private void setListeners() {
        cancelButton.setOnClickListener(view -> {
            closeDialog();
        });

        saveButton.setOnClickListener(view -> {
            if (typeView == TypeView.CREATE)
                tryCreateAndSaveNewGroupAlarm();
            else if (typeView == TypeView.UPDATE)
                tryUpdateGroupAlarm();
        });

        dialog.setOnCancelListener(dialogInterface -> resetViewState());
        dialog.setOnDismissListener(dialogInterface -> resetViewState());
    }

    private void tryUpdateGroupAlarm() {
        if (isNameValid()) {
            GroupAlarmModel gam = createGroupAlarm();
            gam.setId(groupAlarmModel.getId());
            GroupAlarmDataBase.getInstance(dialog.getContext()).updateGroupAlarm(gam);
            if (isGroupAlarmAddedIntoDatabase(gam)) {
                closeDialog();
            } else {
                Toast.makeText(dialog.getContext(), dialog.getContext().getString(R.string.error_connecting_to_database), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void closeDialog() {
        KeyboardUtilities.hideKeyboard(dialog);
        dialog.dismiss();
    }

    private void tryCreateAndSaveNewGroupAlarm() {
        if (isNameValid()) {
            GroupAlarmModel groupAlarmModel = createGroupAlarm();
            saveGroupAlarmToDatabase(groupAlarmModel);
        }
        else {
            nameEditText.setError(dialog.getContext().getString(R.string.name_is_required));
        }
    }

    private boolean isNameValid() {
        return !nameEditText.getText().toString().isEmpty();
    }

    private void saveGroupAlarmToDatabase(GroupAlarmModel groupAlarmModel) {
        GroupAlarmModel ga = GroupAlarmDataBase.getInstance(dialog.getContext()).insertGroupAlarm(groupAlarmModel);
        if (isGroupAlarmAddedIntoDatabase(ga)) {
            closeDialog();
            mainActivityView.showGroupAlarmActivity(ga);
        } else {
            Toast.makeText(dialog.getContext(), dialog.getContext().getString(R.string.error_connecting_to_database), Toast.LENGTH_LONG).show();
        }
    }

    private static boolean isGroupAlarmAddedIntoDatabase(GroupAlarmModel ga) {
        return ga != null && ga.getId() != 0;
    }

    private GroupAlarmModel createGroupAlarm() {
        return GroupAlarmModel.builder().name(nameEditText.getText().toString())
                .note(noteEditText.getText().toString())
                .build();
    }

    private void resetViewState() {
        mainActivityView.hideFullScreenMask();
        mainActivityView.refreshTitleInActionBar();
    }

    private void initView() {
        cancelButton = dialog.findViewById(R.id.cancel_button);
        saveButton = dialog.findViewById(R.id.save_button);
        nameEditText = dialog.findViewById(R.id.group_alarm_name_edit_text);
        noteEditText = dialog.findViewById(R.id.group_alarm_note_edit_text);
    }

    private void initViewForUpdateGroupAlarm() {
        if (typeView == TypeView.UPDATE) {
            nameEditText.setText(groupAlarmModel.getName());
            noteEditText.setText(groupAlarmModel.getNote());
            TextView title = dialog.findViewById(R.id.title_text_view);
            title.setText(context.getString(R.string.update_group_alarm_title));
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }
}
