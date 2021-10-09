package com.niemiec.risingview.view;

import android.content.Context;
import android.widget.TextView;

import com.niemiec.risingview.R;

public class ViewBuilder {
    private final TextView risingSoundDescription;
    private final RisingSoundViewButtons buttons;


    public ViewBuilder(Context context) {
        risingSoundDescription = new TextView(context);
        risingSoundDescription.setText(R.string.rising_sound_description);
        buttons = new RisingSoundViewButtons(context);
    }

    public TextView getTextView() {
        return risingSoundDescription;
    }

    public RisingSoundViewButtons getRisingSoundViewButtons() {
        return buttons;
    }
}
