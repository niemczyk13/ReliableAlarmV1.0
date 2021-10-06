package com.example.alarmschedule.view.alarm.schedule.text;

public class DescriptionOfSelectedDay {
    private static final String[] months = new String[] {
            "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
            "Wrzesień", "Październik", "Listopad", "Grudzień"
    };

    private static final String[] days = new String[] {
            "Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
    };

    public static String getDescription(int day, int month, int dayOfWeek) {
        String m = months[month];
        String d = days[dayOfWeek - 1];
        return "Tylko " + d + "., " + day + " " + m;
    }
}
