package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view;

public enum SafeAlarmLaunchValue {
    NONE("Brak", 0),
    FIRST("5%", 5),
    SECOND("10%", 10),
    THIRD("15%", 15),
    FOURTH("20%", 20);

    private final String name;
    private final int value;

    SafeAlarmLaunchValue(String name, int value) {
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
