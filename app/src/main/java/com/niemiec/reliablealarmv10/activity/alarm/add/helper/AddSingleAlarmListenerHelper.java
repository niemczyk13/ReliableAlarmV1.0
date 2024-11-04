package com.niemiec.reliablealarmv10.activity.alarm.add.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AddSingleAlarmListenerHelper {

    public static void setupListeners(AddSingleAlarmViewHelper viewHelper, AddSingleAlarmActionListener listener) {
        viewHelper.getViewManagement().saveButton.setOnClickListener(view -> listener.saveButtonClick());
        viewHelper.getViewManagement().cancelButton.setOnClickListener(view -> listener.cancelButtonClick());
    }

    public interface AddSingleAlarmActionListener {
        void saveButtonClick();
        void cancelButtonClick();
    }
}
