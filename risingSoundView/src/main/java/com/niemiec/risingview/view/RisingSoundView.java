package com.niemiec.risingview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.niemiec.risingview.logic.RisingSoundViewLogic;
import com.niemiec.risingview.model.RisingSound;

import androidx.annotation.Nullable;

public class RisingSoundView extends LinearLayout {
    private RisingSoundViewLogic logic;
    private ViewBuilder viewBuilder;

    public RisingSoundView(Context context) {
        super(context);
        setProperties();
    }

    public RisingSoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProperties();
    }

    public RisingSoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProperties();
    }

    private void setProperties() {
        viewBuilder = new ViewBuilder(super.getContext());
        addViews();
        createRisingSoundViewLogic(viewBuilder.getRisingSoundViewButtons());
    }

    private void addViews() {
        addView(viewBuilder.getTextView());
        addButtonsInView();
    }

    private void addButtonsInView() {
        for (RisingSoundButton button : viewBuilder.getRisingSoundViewButtons().getButtons()) {
            addView(button);
        }
    }

    private void createRisingSoundViewLogic(RisingSoundViewButtons buttons) {
        logic = new RisingSoundViewLogic(buttons);
    }

    public void initialize(RisingSound risingSound) {
        logic.initialize(risingSound);
    }

    public RisingSound getRisingSound() {
        return logic.getRisingSound();
    }
}
