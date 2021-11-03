package com.niemiec.reliablealarmv10.database.alarm;

import androidx.room.TypeConverter;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converts {
    @TypeConverter
    public static Long calendarToLong(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }

    @TypeConverter
    public static Calendar calendarFromLong(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    @TypeConverter
    public static String daysToList(Map<DayOfWeek, Boolean> days) {
        List<Boolean> list = new ArrayList<>();
        list.add(days.get(DayOfWeek.MONDAY));
        list.add(days.get(DayOfWeek.TUESDAY));
        list.add(days.get(DayOfWeek.WEDNESDAY));
        list.add(days.get(DayOfWeek.THURSDAY));
        list.add(days.get(DayOfWeek.FRIDAY));
        list.add(days.get(DayOfWeek.SATURDAY));
        list.add(days.get(DayOfWeek.SUNDAY));

        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static Map<DayOfWeek, Boolean> daysForList(String json) {
        Type listType = new TypeToken<ArrayList<Boolean>>() {}.getType();
        List<Boolean> list = new Gson().fromJson(json, listType);

        Map<DayOfWeek, Boolean> days = new HashMap<>();
        days.put(DayOfWeek.MONDAY, list.get(0));
        days.put(DayOfWeek.TUESDAY, list.get(1));
        days.put(DayOfWeek.WEDNESDAY, list.get(2));
        days.put(DayOfWeek.THURSDAY, list.get(3));
        days.put(DayOfWeek.FRIDAY, list.get(4));
        days.put(DayOfWeek.SATURDAY, list.get(5));
        days.put(DayOfWeek.SUNDAY, list.get(6));

        return days;
    }
}
