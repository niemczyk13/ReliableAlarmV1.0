package com.niemiec.reliablealarmv10.activity.main.observer;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void toggleView();
    boolean setViewSelected(int position);
}
