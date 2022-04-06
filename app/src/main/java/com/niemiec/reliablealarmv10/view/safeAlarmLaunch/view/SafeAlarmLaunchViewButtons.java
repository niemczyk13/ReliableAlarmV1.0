package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view;

import android.content.Context;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;

import java.util.HashMap;
import java.util.Map;

//TODO
public class SafeAlarmLaunchViewButtons {
    private final Context context;
    private Map<Integer, SafeAlarmLaunchButton> buttons;
    private int checkedButtonId;
    private SafeAlarmLaunchButtonClickListener listener;

    public SafeAlarmLaunchViewButtons(Context context) {
        this.context = context;
        createSafeAlarmLaunchButtons();
    }

    private void createSafeAlarmLaunchButtons() {
        buttons = new HashMap<>();
        SafeAlarmLaunchValue[] values = SafeAlarmLaunchValue.values();
        for (SafeAlarmLaunchValue value : values) {
            SafeAlarmLaunchButton button = new SafeAlarmLaunchButton(value.getName(), value.getValue(), context);
            button.addClickSafeAlarmLaunchButtonClickListener(this::onClick);
            buttons.put(button.getId(), button);
        }
    }

    private void onClick(int clickButtonId) {
        for (SafeAlarmLaunchButton button : buttons.values()) {
            if (button.getId() == clickButtonId) {
                button.setChecked();
            } else {
                button.setUnchecked();
            }
        }
        checkedButtonId = clickButtonId;

        listener.onClick();
    }

    public void setSafeAlarmLaunch(SafeAlarmLaunch safeAlarmLaunch) {
        for (SafeAlarmLaunchButton button : buttons.values()) {
            if (button.getPercentageValue() == safeAlarmLaunch.getSafeAlarmLaunchPercentage()) {
                button.setChecked();
                checkedButtonId = button.getId();
            }
        }
    }

    public SafeAlarmLaunchButton[] getButtons() {
        return buttons.values().toArray(new SafeAlarmLaunchButton[0]);
    }

    public interface SafeAlarmLaunchButtonClickListener {
        void onClick();
    }

    public void setSafeAlarmLaunchClickListener(SafeAlarmLaunchButtonClickListener listener) {
        this.listener = listener;
    }

    public SafeAlarmLaunch getSafeAlarmLaunch() {
        SafeAlarmLaunchButton button = buttons.get(checkedButtonId);
        assert button != null;
        SafeAlarmLaunch safeAlarmLaunch = new SafeAlarmLaunch();
        safeAlarmLaunch.setSafeAlarmLaunchPercentage(button.getPercentageValue());
        return safeAlarmLaunch;
    }
}
