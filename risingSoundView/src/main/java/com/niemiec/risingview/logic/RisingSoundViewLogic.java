package com.niemiec.risingview.logic;

import com.niemiec.risingview.model.RisingSound;
import com.niemiec.risingview.view.RisingSoundViewButtons;

public class RisingSoundViewLogic {
    private RisingSound risingSound;
    private final RisingSoundViewButtons buttons;
    public RisingSoundViewLogic(RisingSoundViewButtons buttons) {
        this.buttons = buttons;
    }

    public void initialize(RisingSound risingSound) {
        buttons.setRisingSound(risingSound);
        buttons.setRisingSoundButtonClickListener(this::onRisingButtonClick);
    }

    private void onRisingButtonClick() {
        risingSound = buttons.getRisingSound();
    }

    public RisingSound getRisingSound() {
        return risingSound;
    }
}
