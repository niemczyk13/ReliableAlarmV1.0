package com.example.alarmsoundview.activity.sound.select;

public class BuiltInSound {
    private boolean checked = false;
    private final String name;
    private final int id;

    public BuiltInSound(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setChecked() {
        checked = true;
    }
    public void setUnchecked() {
        checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
