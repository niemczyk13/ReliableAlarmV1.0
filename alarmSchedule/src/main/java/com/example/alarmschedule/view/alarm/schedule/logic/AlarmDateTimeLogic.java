package com.example.alarmschedule.view.alarm.schedule.logic;

import androidx.fragment.app.FragmentManager;

import com.example.alarmschedule.view.alarm.schedule.view.CalendarImageButton;
import com.example.alarmschedule.view.alarm.schedule.view.DaysButtons;
import com.example.alarmschedule.view.alarm.schedule.view.InfoTextView;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;

public class AlarmDateTimeLogic {
    private final DaysButtons daysButtons;
    private final InfoTextView infoTextView;
    private final CalendarImageButton calendarImageButton;

    public AlarmDateTimeLogic(DaysButtons daysButtons, InfoTextView infoTextView, CalendarImageButton calendarImageButton) {
        this.daysButtons = daysButtons;
        this.infoTextView = infoTextView;
        this.calendarImageButton = calendarImageButton;

        addOnClickDayButtonListener();
        addOnClickCalendarImageButtonListener();
        addOnClickUncheckAllDaysButtonsListener();
    }

    private void addOnClickUncheckAllDaysButtonsListener() {
        daysButtons.addOnClickUncheckAllDaysButtonsListener(() -> {
            AlarmDateTime alarmDateTime = AlarmDateTimeUpdater.updateForAllDaysUncheck();
            infoTextView.showInfoText(alarmDateTime);
        });
    }

    public void initialize(AlarmDateTime adt, FragmentManager supportFragmentManager) {
        AlarmDateTime alarmDateTime = AlarmDateTimeUpdater.update(adt);
        daysButtons.setWeek(alarmDateTime.getWeek());
        calendarImageButton.setFragmentManager(supportFragmentManager);
        infoTextView.showInfoText(alarmDateTime);
    }

    private void addOnClickDayButtonListener() {
        daysButtons.addOnClickDayButtonListener(() -> {
            AlarmDateTime alarmDateTime = AlarmDateTimeUpdater.updateWeek(daysButtons.getWeek());
            infoTextView.showInfoText(alarmDateTime);
        });
    }


    private void addOnClickCalendarImageButtonListener() {
        calendarImageButton.addCalendarImageButtonClickListener(() -> {
            AlarmDateTime alarmDateTime = AlarmDateTimeUpdater.getAlarmDateTime();
            calendarImageButton.setAlarmDateTime(alarmDateTime.getDateTime());
        });
    }

    public void setTime(int hour, int minute) {
        AlarmDateTime alarmDateTime = AlarmDateTimeUpdater.updateTime(hour, minute);
        infoTextView.showInfoText(alarmDateTime);
    }

    public void setDate(int year, int month, int day) {
        AlarmDateTime alarmDateTime = AlarmDateTimeUpdater.updateDate(year, month, day);
        daysButtons.uncheckWeek();
        infoTextView.showInfoText(alarmDateTime);
    }

    public AlarmDateTime getAlarmDateTime() {
        return AlarmDateTimeUpdater.getFinalVersionAlarmDateTime();
    }
}
