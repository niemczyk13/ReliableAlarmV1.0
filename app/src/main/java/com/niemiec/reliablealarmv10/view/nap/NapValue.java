package com.niemiec.reliablealarmv10.view.nap;

import com.niemiec.reliablealarmv10.R;

public enum NapValue {
    LACK("BRAK", 0),
    FIRST("1 minuta", 1),
    SECOND("2 minuty", 2),
    THIRD("3 minuty", 3),
    FOURTH("4 minuty", 4),
    FIFTH("5 minut", 5);

    private final String name;
    private final int value;

    NapValue(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
