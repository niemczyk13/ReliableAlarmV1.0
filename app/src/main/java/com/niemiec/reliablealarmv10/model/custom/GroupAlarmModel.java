package com.niemiec.reliablealarmv10.model.custom;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;

import java.util.Calendar;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupAlarmModel implements Alarm {
    private long id;
    private String name;
    private String note;
    private boolean isActive;
    private List<SingleAlarmModel> alarms;

    //TODO dopracować z tym porównaniem

    public int compareTimeTo(Alarm alarm) {
        SingleAlarmModel earlierAlarm = getEarliestAlarm();
        if (earlierAlarm == null) return -1;
        return earlierAlarm.compareTimeTo(alarm);
    }

    @Override
    public int compareDateTimeTo(Alarm alarm) {
        SingleAlarmModel earlierAlarm = getEarliestAlarm();
        if (earlierAlarm == null) return -1;
        return earlierAlarm.getAlarmDateTime().getDateTime().compareTo(alarm.getAlarmDateTime().getDateTime());
    }

    @Override
    public AlarmDateTime getAlarmDateTime() {
        if (alarms.isEmpty()) return new AlarmDateTime(Calendar.getInstance(), new Week());
        return getEarliestAlarm().getAlarmDateTime();
    }


    public SingleAlarmModel getEarliestAlarm() {
        if (alarms.isEmpty()) return null;
        SingleAlarmModel earlierAlarm = alarms.get(0);
        for (SingleAlarmModel alarm : alarms) {
            if (alarm.compareDateTimeTo(earlierAlarm) < 0) {
                earlierAlarm = alarm;
            }
        }
        return earlierAlarm;
    }
}
