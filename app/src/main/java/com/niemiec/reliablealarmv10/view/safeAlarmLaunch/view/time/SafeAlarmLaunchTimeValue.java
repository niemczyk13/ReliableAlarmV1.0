package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.time;

public enum SafeAlarmLaunchTimeValue {
    LACK("BRAK", 0),
    FIRST("1 godzina", 1),
    SECOND("2 godziny", 2),
    THIRD("3 godziny", 3),
    FOURTH("4 godziny", 4),
    FIFTH("5 godzin", 5),
    SIXTH("6 godzin", 6),
    SEVENTH("7 godzin", 7),
    EIGHTH("8 godzin", 8),
    NINTH("9 godzin", 9),
    TENTH("10 godzin", 10);


    private final String name;
    private final int value;

    SafeAlarmLaunchTimeValue(String name, int value) {
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
