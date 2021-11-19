package com.niemiec.reliablealarmv10.activity.main.view.toggle;

import com.niemiec.reliablealarmv10.activity.main.observer.ObserverPattern;

public class ToggleBetweenDeleteAndEditViews implements ObserverPattern.Subject {
    private DisplayState displayState;
    private ObserverPattern.Observer observer;

    public ToggleBetweenDeleteAndEditViews() {
    }

    @Override
    public void attach(ObserverPattern.Observer observer) {
        this.observer = observer;
    }

    @Override
    public void detach(ObserverPattern.Observer observer) {
        this.observer = null;
    }

    @Override
    public void toggleView() {
        observer.toggleView();
    }

    @Override
    public boolean setViewSelected(int position) {
        return observer.showSelectedItem(position);
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
