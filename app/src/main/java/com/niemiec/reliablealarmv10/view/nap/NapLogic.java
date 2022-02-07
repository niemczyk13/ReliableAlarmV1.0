package com.niemiec.reliablealarmv10.view.nap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.niemiec.reliablealarmv10.view.nap.model.Nap;

public class NapLogic {
    private Nap nap;
    private Spinner spinner;
    private int[] values = {NapValue.LACK.getValue(), NapValue.FIRST.getValue(),
            NapValue.SECOND.getValue(), NapValue.THIRD.getValue(),
            NapValue.FOURTH.getValue(), NapValue.FIFTH.getValue()};

    public NapLogic(Spinner spinner) {
        this.spinner = spinner;
    }

    public void initialize(Nap nap) {
        this.nap = nap;
        showSelectionNap();
        setOnItemSelectedListener();
    }

    private void showSelectionNap() {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == nap.getNapTime()) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setOnItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nap.setNapTime(values[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public Nap getNap() {
        return nap;
    }
}
