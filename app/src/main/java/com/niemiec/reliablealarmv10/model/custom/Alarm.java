package com.niemiec.reliablealarmv10.model.custom;

public interface Alarm {
    long getId();
    String getName();
    String getNote();
    boolean isActive();
}
