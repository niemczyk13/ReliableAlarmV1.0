package com.niemiec.reliablealarmv10.activity.main.view.toggle;

import com.niemiec.reliablealarmv10.activity.main.observer.Observer;
import com.niemiec.reliablealarmv10.activity.main.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class ToggleBetweenDeleteAndEditViews implements Subject {
    private DisplayState displayState;
    private List<Observer> observers;

    public ToggleBetweenDeleteAndEditViews() {
        observers = new ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        int index = observers.indexOf(observer);
        observers.remove(index);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void displayTheEditView() {
        if (displayState == DisplayState.DELETE_VIEW) {
            //TODO

            displayState = DisplayState.EDITING_VIEW;
        }
    }

    public void displayTheDeleteView() {
        if (displayState == DisplayState.EDITING_VIEW) {
            //TODO

            displayState = DisplayState.DELETE_VIEW;
        }
    }

    public enum DisplayState {
        DELETE_VIEW, EDITING_VIEW;
    }
}
