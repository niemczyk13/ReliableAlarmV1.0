package com.niemiec.reliablealarmv10.activity;

public abstract class BasePresenter<T> {
    public T view;

    public void attach(T view) {this.view = view;}

    public void detach() {this.view = null;}

    public boolean isViewAttached() {return  this.view != null;}
}
