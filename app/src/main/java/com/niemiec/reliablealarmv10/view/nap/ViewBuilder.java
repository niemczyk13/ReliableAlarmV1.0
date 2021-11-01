package com.niemiec.reliablealarmv10.view.nap;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;

public class ViewBuilder {
    private TextView napDescription;
    private final Spinner napSpinner;

    public ViewBuilder(Context context) {
        createNapDescriptionTextView(context);
        napSpinner = createNapSpinner(context);  
    }

    private Spinner createNapSpinner(Context context) {
        Spinner spinner = new Spinner(context);
        spinner.setGravity(Gravity.RIGHT);

        String[] str = {NapValue.FIRST.getName(), NapValue.SECOND.getName(),
                NapValue.THIRD.getName(), NapValue.FOURTH.getName(), NapValue.FIFTH.getName()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, str);
        spinner.setAdapter(adapter);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        spinner.setLayoutParams(params);

        return spinner;
    }

    private void createNapDescriptionTextView(Context context) {
        napDescription = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.weight = 1;
        napDescription.setLayoutParams(params);
        napDescription.setText("Drzemka:");
        //napDescription.setGravity(Gravity.START);
    }

    public TextView getNapDescription() {
        return napDescription;
    }

    public Spinner getNapSpinner() {
        return napSpinner;
    }
}
