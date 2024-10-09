package com.niemiec.reliablealarmv10.activity.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBaseModel;
import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;

public class CreateNewGroupAlarmDialog {
    private Dialog dialog;
    private Button cancelButton;
    private Button saveButton;
    private EditText nameEditText;
    private EditText noteEditText;

    public CreateNewGroupAlarmDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_group_alarm_dialog);
        initView();
        setListeners();
    }

    public void show() {
        dialog.show();
    }

    private void setListeners() {
        cancelButton.setOnClickListener(view -> dialog.dismiss());

        saveButton.setOnClickListener(view -> {

            if (!nameEditText.getText().toString().isEmpty()) {
                GroupAlarmModel groupAlarmModel = GroupAlarmModel.builder().name(nameEditText.getText().toString())
                        .note(noteEditText.getText().toString())
                        .build();

                GroupAlarmDataBase.getInstance(dialog.getContext()).insertGroupAlarm(groupAlarmModel);

            //TODO tworzymy alarm grypowy i otwieramy nową aktywność dla alarmu grupowego
            //TODO otwieramy nową aktywność
            dialog.dismiss();
            }
            else {
                //TODO wyświetlenie komunikatu, że trzeba uzupełnić name
            }
        });
    }

    private void initView() {
        cancelButton = dialog.findViewById(R.id.cancel_button);
        saveButton = dialog.findViewById(R.id.save_button);
        nameEditText = dialog.findViewById(R.id.group_alarm_name_edit_text);
        noteEditText = dialog.findViewById(R.id.group_alarm_note_edit_text);
    }
}
