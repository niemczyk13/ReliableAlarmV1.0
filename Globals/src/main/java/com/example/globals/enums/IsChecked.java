package com.example.globals.enums;

public enum IsChecked {
    CHECKED(true),
    UNCHECKED(false);

    private final boolean value;

    IsChecked(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

}
