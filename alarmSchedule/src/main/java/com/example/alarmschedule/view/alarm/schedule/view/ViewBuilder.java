package com.example.alarmschedule.view.alarm.schedule.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

public class ViewBuilder {
    private final Context context;
    private LinearLayout firstLineLayout;
    private LinearLayout secondLineLayout;

    private InfoTextView infoTextView;
    private CalendarImageButton calendarImageButton;
    private DaysButtons daysButtons;

    public ViewBuilder(Context context) {
        this.context = context;
        createLayouts();
        createViews();
        addViewsToLayout();
    }

    private void createLayouts() {
        createFirstLayout();
        createSecondLayout();
    }

    private void createFirstLayout() {
        firstLineLayout = new LinearLayout(context);
        firstLineLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    private void createSecondLayout() {
        secondLineLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 1;
        params.bottomMargin = 1;
        secondLineLayout.setLayoutParams(params);
        secondLineLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    private void createViews() {
        infoTextView = new InfoTextView(context, 12);
        calendarImageButton = new CalendarImageButton(context, 1);
        daysButtons = new DaysButtons(context);
    }

    private void addViewsToLayout() {
        addViewsToFirstLayout();
        addViewsToSecondLayout();
    }

    private void addViewsToFirstLayout() {
        firstLineLayout.addView(infoTextView.getView());
        firstLineLayout.addView(calendarImageButton.getView());
    }

    private void addViewsToSecondLayout() {
        setDaysButtonsToSecondLinearLayout();
    }

    private void setDaysButtonsToSecondLinearLayout() {
        for (MaterialButton button : daysButtons.getDaysButtons()) {
            secondLineLayout.addView(button);
        }
        secondLineLayout.addView(daysButtons.getCheckAllDaysButton());
    }

    public LinearLayout getFirstLineLayout() {
        return firstLineLayout;
    }

    public LinearLayout getSecondLineLayout() {
        return secondLineLayout;
    }

    public InfoTextView getInfoTextView() {
        return infoTextView;
    }

    public CalendarImageButton getCalendarImageButton() {
        return calendarImageButton;
    }

    public DaysButtons getDaysButtons() {
        return daysButtons;
    }
}
