package com.niemiec.risingview.view;

public enum RisingSoundValue {
    NONE("Brak",0),
    FIRST("30s",30),
    SECOND("60s",60),
    THIRD("90s",90);

    private final String name;
    private final int value;

    RisingSoundValue(String name, int value) {
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
