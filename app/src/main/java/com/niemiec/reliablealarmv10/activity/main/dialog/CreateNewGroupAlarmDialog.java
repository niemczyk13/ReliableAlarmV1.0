package com.niemiec.reliablealarmv10.activity.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.main.MainContractMVP;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBaseModel;
import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;

public class CreateNewGroupAlarmDialog {
    private final MainContractMVP.View mainActivityView;
    private final Dialog dialog;
    private Button cancelButton;
    private Button saveButton;
    private EditText nameEditText;
    private EditText noteEditText;

    public CreateNewGroupAlarmDialog(MainContractMVP.View mainActivityView, Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_group_alarm_dialog);
        this.mainActivityView = mainActivityView;
        initView();
        setListeners();

        Window window = dialog.getWindow();
        if (window != null) {
            // Nie dodawaj FLAG_DIM_BEHIND, aby zapobiec przyciemnieniu tła
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f; // Ustaw wartość na 0.0
            window.setAttributes(layoutParams);
        }
    }

    public void show() {
        dialog.show();
    }

    private void setListeners() {
        cancelButton.setOnClickListener(view -> {
            dialog.dismiss();
        });

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

        dialog.setOnCancelListener(dialogInterface -> mainActivityView.hideFullScreenMask());
        dialog.setOnDismissListener(dialogInterface -> mainActivityView.hideFullScreenMask());
    }

    private void initView() {
        cancelButton = dialog.findViewById(R.id.cancel_button);
        saveButton = dialog.findViewById(R.id.save_button);
        nameEditText = dialog.findViewById(R.id.group_alarm_name_edit_text);
        noteEditText = dialog.findViewById(R.id.group_alarm_note_edit_text);
    }
}