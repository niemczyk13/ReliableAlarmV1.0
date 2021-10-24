package com.niemiec.reliablealarmv10.view.nap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.niemiec.reliablealarmv10.view.nap.model.Nap;

public class NapLogic {
    private Nap nap;
    private Spinner spinner;

    public NapLogic(Spinner spinner) {
        this.spinner = spinner;
    }

    public void initialize(Nap nap) {
        this.nap = nap;
        spinner.setOnItemClickListener(this::onItemClickListener);
    }

    private void onItemClickListener(AdapterView<?> adapterView, View view, int i, long l) {
        int[] values = {NapValue.FIRST.getValue(),
        NapValue.SECOND.getValue(), NapValue.THIRD.getValue(),
        NapValue.FOURTH.getValue(), NapValue.FIFTH.getValue()};

        nap.setValue(values[i]);
    }

    public Nap getNap() {
        return nap;
    }
}
