package com.niemiec.reliablealarmv10.activity.main.observer;

public interface ObserverPattern {
    interface Observer {
        void toggleView();
        boolean showSelectedItem(int position);
    }

    interface Subject {
        void attach(com.niemiec.reliablealarmv10.activity.main.observer.ObserverPattern.Observer observer);
        void detach(com.niemiec.reliablealarmv10.activity.main.observer.ObserverPattern.Observer observer);
        void toggleView();
        boolean setViewSelected(int position);
    }

}
