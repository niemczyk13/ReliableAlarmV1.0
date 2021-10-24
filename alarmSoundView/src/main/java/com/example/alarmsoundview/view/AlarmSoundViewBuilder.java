package com.example.alarmsoundview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlarmSoundViewBuilder {
    private final Context context;
    private TextView description;
    private TextView name;

    public AlarmSoundViewBuilder(Context context) {
        this.context = context;
        createDescriptionTextView();
        createNameTextView();
    }

    @SuppressLint("RtlHardcoded")
    private void createNameTextView() {
        name = new TextView(context);
        LinearLayout.LayoutParams params = createNameTextViewParams();
        name.setGravity(Gravity.RIGHT);
        name.setLayoutParams(params);
    }

    private LinearLayout.LayoutParams createNameTextViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }

    private void createDescriptionTextView() {
        description = new TextView(context);
        LinearLayout.LayoutParams params = createDescriptionTextViewParams();
        description.setLayoutParams(params);
    }

    private LinearLayout.LayoutParams createDescriptionTextViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        return params;
    }

    public TextView getDescriptionTextView() {
        return description;
    }

    public TextView getNameTextView() {
        return name;
    }
}
