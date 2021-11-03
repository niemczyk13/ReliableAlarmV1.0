package com.niemiec.risingview.view;

import android.content.Context;

import com.niemiec.risingview.model.RisingSound;

import java.util.HashMap;
import java.util.Map;

public class RisingSoundViewButtons {
    private final Context context;
    private Map<Integer, RisingSoundButton> buttons;
    private int checkedButtonId;
    private RisingSoundButtonClickListener listener;

    public RisingSoundViewButtons(Context context) {
        this.context = context;
        createRisingSoundButtons();
    }

    private void createRisingSoundButtons() {
        buttons = new HashMap<>();
        RisingSoundValue[] values = RisingSoundValue.values();
        for (RisingSoundValue value : values) {
            RisingSoundButton button = new RisingSoundButton(value.getName(), value.getValue(), context);
            button.addClickRisingSoundButtonClickListener(this::onClick);
            buttons.put(button.getId(), button);
        }
    }

    private void onClick(int clickButtonId) {
        for (RisingSoundButton button : buttons.values()) {
            if (button.getId() == clickButtonId) {
                button.setChecked();
            } else {
                button.setUnchecked();
            }
        }
        checkedButtonId = clickButtonId;

        listener.onClick();
    }

    public void setRisingSound(RisingSound risingSound) {
        for (RisingSoundButton button : buttons.values()) {
            if (button.getValue() == risingSound.getTime()) {
                button.setChecked();
                checkedButtonId = button.getId();
            }
        }
    }

    public RisingSoundButton[] getButtons() {
        return buttons.values().toArray(new RisingSoundButton[0]);
    }

    public interface RisingSoundButtonClickListener {
        void onClick();
    }

    public void setRisingSoundButtonClickListener(RisingSoundButtonClickListener listener) {
        this.listener = listener;
    }

    public RisingSound getRisingSound() {
        RisingSoundButton button = buttons.get(checkedButtonId);
        assert button != null;
        RisingSound risingSound = new RisingSound();
        risingSound.setTime(button.getValue());
        return risingSound;
    }
}
