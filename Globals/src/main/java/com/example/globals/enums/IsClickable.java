package com.example.globals.enums;

public enum IsClickable {
    CLICKABLE(true),
    NON_CLICKABLE(false);

    private boolean isClickable;

    IsClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public boolean value() {
        return isClickable;
    }
}