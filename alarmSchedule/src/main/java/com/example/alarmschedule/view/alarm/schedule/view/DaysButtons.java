package com.example.alarmschedule.view.alarm.schedule.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.alarmschedule.R;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.google.android.material.button.MaterialButton;

public class DaysButtons {
    private final Context context;
    private MaterialButton[] daysButtons;
    private ImageButton checkAllDaysButton;
    private String[] daysNames;
    private OnClickDayButtonListener onClickDayButtonListener;
    private OnClickUncheckAllDaysButtonsListener onClickUncheckAllDaysButtonsListener;

    public DaysButtons(Context context) {
        this.context = context;
        createDaysNames();
        createButtons();
    }

    private void createDaysNames() {
        daysNames = new String[7];
        daysNames[0] = "Pn";
        daysNames[1] = "Wt";
        daysNames[2] = "Sr";
        daysNames[3] = "Cz";
        daysNames[4] = "Pt";
        daysNames[5] = "So";
        daysNames[6] = "N";
    }

    private void createButtons() {
        createDaysButtons();
        createCheckAllDaysButton();
    }

    private void createDaysButtons() {
        daysButtons = new MaterialButton[7];
        for (int i = 0; i < daysButtons.length; i++) {
            MaterialButton button = createDayButton(daysNames[i]);
            daysButtons[i] = button;
        }
    }

    private MaterialButton createDayButton(String daysName) {
        MaterialButton button = new MaterialButton(context);
        button.setText(daysName);
        LinearLayout.LayoutParams params = getDefaultLayoutParamsForDayButton();
        button.setLayoutParams(params);
        button.setPadding(0, 0, 0, 0);
        button.setCheckable(true);
        setUncheckColorButton(button);
        button.setId(View.generateViewId());
        button.setOnClickListener(this::onDayButtonClick);
        return button;
    }

    private LinearLayout.LayoutParams getDefaultLayoutParamsForDayButton() {
        final float scale = context.getResources().getDisplayMetrics().density;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 2;
        params.leftMargin = 1;
        params.rightMargin = 1;
        int dp = 55;
        params.height = (int) ((dp * scale) + 0.5f);

        return params;
    }

    private void setUncheckColorButton(MaterialButton button) {
        button.setBackgroundColor(DataFromMainThemeUtils.getColorFromTheme(context, R.attr.colorSecondary));
        button.setTextColor(DataFromMainThemeUtils.getColorFromTheme(context, R.attr.colorOnSecondary));
    }

    private void onDayButtonClick(View view) {
        for (MaterialButton button : daysButtons) {
            followTheStepsForTheButtonClicked(button, view);
        }

        if (onClickDayButtonListener != null) {
            onClickDayButtonListener.onClick();
        }
    }

    private void followTheStepsForTheButtonClicked(MaterialButton button, View view) {
        if (button.getId() == view.getId()) {
            button.setChecked(button.isChecked());
            changeButtonView(button);
        }
    }

    private void changeButtonView(MaterialButton button) {
        if (button.isChecked()) {
            setCheckColorButton(button);
        } else {
            setUncheckColorButton(button);
        }
    }

    private void setCheckColorButton(MaterialButton button) {
        button.setBackgroundColor(DataFromMainThemeUtils.getColorFromTheme(context, R.attr.colorPrimary));
        button.setTextColor(DataFromMainThemeUtils.getColorFromTheme(context, R.attr.colorOnPrimary));
    }

    private void createCheckAllDaysButton() {
        checkAllDaysButton = new ImageButton(context);
        LinearLayout.LayoutParams params = getDefaultLayoutParamsForCheckAllDayButton();
        checkAllDaysButton.setLayoutParams(params);
        checkAllDaysButton.setColorFilter(DataFromMainThemeUtils.getColorFromTheme(context, R.attr.colorSecondary));
        checkAllDaysButton.setBackgroundColor(DataFromMainThemeUtils.getColorFromTheme(context, R.attr.colorOnSecondary));
        checkAllDaysButton.setPadding(0, 0, 0, 0);

        checkAllDaysButton.setId(View.generateViewId());
        checkAllDaysButton.setOnClickListener(this::onAllDaysButtonClick);
        checkAllDaysButton.setImageResource(R.drawable.ic_baseline_select_all_24);
        //checkAllDays.setBackground(ContextCompat.getDrawable(context, R.drawable.day_button));
    }

    private LinearLayout.LayoutParams getDefaultLayoutParamsForCheckAllDayButton() {
        final float scale = context.getResources().getDisplayMetrics().density;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 3;
        params.leftMargin = 2;
        int dp = 55;
        params.height = (int) ((dp * scale) + 0.5f);
        int dp2 = 100;
        params.width = (int) ((dp2 * scale) + 0.5f);

        return params;
    }

    private void onAllDaysButtonClick(View view) {
        if (allDayButtonIsChecked()) {
            uncheckAllDays();
            if (onClickUncheckAllDaysButtonsListener != null) {
                onClickUncheckAllDaysButtonsListener.uncheckAll();
            }
        } else {
            checkAllDaysButtons();
            if (onClickDayButtonListener != null) {
                onClickDayButtonListener.onClick();
            }
        }
    }

    private boolean allDayButtonIsChecked() {
        int count = 0;
        for (MaterialButton button : daysButtons) {
            if (button.isChecked()) {
                count++;
            }
        }

        return count == daysButtons.length;
    }

    public void uncheckAllDays() {
        for (MaterialButton button : daysButtons) {
            button.setChecked(false);
            setUncheckColorButton(button);
        }
    }

    private void checkAllDaysButtons() {
        for (MaterialButton button : daysButtons) {
            button.setChecked(true);
            setCheckColorButton(button);
        }
    }

    public MaterialButton[] getDaysButtons() {
        return daysButtons;
    }

    public ImageButton getCheckAllDaysButton() {
        return checkAllDaysButton;
    }

    public boolean isSchedule() {
        for (MaterialButton button : daysButtons) {
            if (button.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public Week getWeek() {
        Week week = new Week();
        week.setDay(DayOfWeek.MONDAY, daysButtons[0].isChecked());
        week.setDay(DayOfWeek.TUESDAY, daysButtons[1].isChecked());
        week.setDay(DayOfWeek.WEDNESDAY, daysButtons[2].isChecked());
        week.setDay(DayOfWeek.THURSDAY, daysButtons[3].isChecked());
        week.setDay(DayOfWeek.FRIDAY, daysButtons[4].isChecked());
        week.setDay(DayOfWeek.SATURDAY, daysButtons[5].isChecked());
        week.setDay(DayOfWeek.SUNDAY, daysButtons[6].isChecked());

        return week;
    }

    public void setWeek(Week weekSchedule) {
        daysButtons[0].setChecked(weekSchedule.dayIsChecked(DayOfWeek.MONDAY));
        daysButtons[1].setChecked(weekSchedule.dayIsChecked(DayOfWeek.TUESDAY));
        daysButtons[2].setChecked(weekSchedule.dayIsChecked(DayOfWeek.WEDNESDAY));
        daysButtons[3].setChecked(weekSchedule.dayIsChecked(DayOfWeek.THURSDAY));
        daysButtons[4].setChecked(weekSchedule.dayIsChecked(DayOfWeek.FRIDAY));
        daysButtons[5].setChecked(weekSchedule.dayIsChecked(DayOfWeek.SATURDAY));
        daysButtons[6].setChecked(weekSchedule.dayIsChecked(DayOfWeek.SUNDAY));

        for (MaterialButton button: daysButtons) {
            if (button.isChecked()) {
                setCheckColorButton(button);
            }
        }

    }

    public void uncheckWeek() {
        daysButtons[0].setChecked(false);
        daysButtons[1].setChecked(false);
        daysButtons[2].setChecked(false);
        daysButtons[3].setChecked(false);
        daysButtons[4].setChecked(false);
        daysButtons[5].setChecked(false);
        daysButtons[6].setChecked(false);

        for (MaterialButton button: daysButtons) {
            setUncheckColorButton(button);
        }
    }

    public void addOnClickDayButtonListener(OnClickDayButtonListener onClickDayButtonListener) {
        this.onClickDayButtonListener = onClickDayButtonListener;
    }

    public interface OnClickDayButtonListener {
        void onClick();
    }

    public void addOnClickUncheckAllDaysButtonsListener(OnClickUncheckAllDaysButtonsListener listener) {
        this.onClickUncheckAllDaysButtonsListener = listener;
    }

    public interface OnClickUncheckAllDaysButtonsListener {
        void uncheckAll();
    }
}
