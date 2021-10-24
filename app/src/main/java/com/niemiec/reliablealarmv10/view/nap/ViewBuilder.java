package com.niemiec.reliablealarmv10.view.nap;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
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
        spinner.setGravity(Gravity.END);

        String[] str = {NapValue.FIRST.getName(), NapValue.SECOND.getName(),
                NapValue.THIRD.getName(), NapValue.FOURTH.getName(), NapValue.FIFTH.getName()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, str);
        spinner.setAdapter(adapter);

        return spinner;
    }

    private void createNapDescriptionTextView(Context context) {
        napDescription = new TextView(context);
        napDescription.setText("Drzemka:");

        napDescription.setGravity(Gravity.START);
    }

    public TextView getNapDescription() {
        return napDescription;
    }

    public Spinner getNapSpinner() {
        return napSpinner;
    }
}
