package com.niemiec.reliablealarmv10.view.nap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.niemiec.reliablealarmv10.view.nap.model.Nap;

import androidx.annotation.Nullable;

public class NapView extends LinearLayout {
    private Nap nap;
    private NapLogic logic;
    private ViewBuilder viewBuilder;

    public NapView(Context context) {
        super(context);
        setProperties();
    }

    public NapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProperties();
    }

    public NapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProperties();
    }

    private void setProperties() {
        super.setGravity(Gravity.RIGHT);
        viewBuilder = new ViewBuilder(super.getContext());
        addViews();
    }

    private void addViews() {
        super.addView(viewBuilder.getNapDescription());
        super.addView(viewBuilder.getNapSpinner());
    }

    public void initialize(Nap nap) {
        logic.initialize(nap);
    }

    public Nap getNap() {
        return logic.getNap();
    }
}
