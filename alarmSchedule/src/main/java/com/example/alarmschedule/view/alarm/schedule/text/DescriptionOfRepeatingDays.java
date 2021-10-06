package com.example.alarmschedule.view.alarm.schedule.text;

import java.util.ArrayList;
import java.util.List;

public class DescriptionOfRepeatingDays {
    private static final int EVERYDAY = 7;
    private static final int FIRST_DAY = 0;
    private static final int PRONOUN_EVERY_MEN_IN_POLISH = 0;
    private static final int PRONOUN_EVERY_WOMEN_IN_POLISH = 1;
    private static final int PRONOUN_EVERY_DAY = 2;

    private static final String[] article = new String[] {"Każdy", "Każda", "Codziennie"};

    private static final String[] fullNamesOfDays = new String[] {"Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
    private static final String[] abbreviatedNamesOfDays = new String[] {"Pn", "Wt", "Śr", "Cz", "Pt", "So", "Nd"};

    public static String getDescription(List<Boolean> days) {
        List<String> checkedDaysNames = getCheckedDaysNames(days);
        if (allDaysAreSelected(checkedDaysNames)) {
            return writeOutThePronounEveryDay();
        } else {
            return createTextForLessThan7Days(checkedDaysNames);
        }
    }

    private static List<String> getCheckedDaysNames(List<Boolean> days) {
        List<Integer> selectedDaysNumber = getNumberOfSelectedDays(days);

        List<String> daysNames;
        int countCheckedDays = selectedDaysNumber.size();
        if (selected1Or2Days(countCheckedDays)) {
            daysNames = getNamesFor1or2Days(selectedDaysNumber);
        } else {
            daysNames = getNamesForMoreThan2Days(selectedDaysNumber);
        }

        return daysNames;
    }

    private static List<Integer> getNumberOfSelectedDays(List<Boolean> days) {
        List<Integer> selectedDaysNumber = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i)) {
                selectedDaysNumber.add(i);
            }
        }
        return selectedDaysNumber;
    }

    private static boolean selected1Or2Days(int countCheckedDays) {
        return countCheckedDays == 1 || countCheckedDays == 2;
    }

    private static List<String> getNamesFor1or2Days(List<Integer> selectedDaysNumber) {
        List<String> daysNames = new ArrayList<>();
        for (int i = 0; i < selectedDaysNumber.size(); i++) {
            daysNames.add(fullNamesOfDays[selectedDaysNumber.get(i)]);
        }
        return daysNames;
    }

    private static List<String> getNamesForMoreThan2Days(List<Integer> selectedDaysNumber) {
        List<String> daysNames = new ArrayList<>();
        for (int i = 0; i < selectedDaysNumber.size(); i++) {
            daysNames.add(abbreviatedNamesOfDays[selectedDaysNumber.get(i)]);
        }
        return  daysNames;
    }

    private static boolean allDaysAreSelected(List<String> checkedDaysNames) {
        return checkedDaysNames.size() == EVERYDAY;
    }

    private static String writeOutThePronounEveryDay() {
        return article[PRONOUN_EVERY_DAY];
    }

    private static String createTextForLessThan7Days(List<String> checkedDaysNames) {
        String text;
        if (nameDayHasTheAppropriateEndingsChar(checkedDaysNames.get(FIRST_DAY))) {
            text = article[PRONOUN_EVERY_WOMEN_IN_POLISH];
        } else {
            text = article[PRONOUN_EVERY_MEN_IN_POLISH];
        }
        text += createText(checkedDaysNames);

        return text;
    }

    private static boolean nameDayHasTheAppropriateEndingsChar(String d) {
        char a = d.charAt(d.length() - 1);
        return a == 'a' || a == 'r' || a == 'd';
    }

    private static String createText(List<String> checkedDaysNames) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < checkedDaysNames.size(); i++) {
            if (i == 0)
                text.append(" ").append(checkedDaysNames.get(i));
            else if (i < checkedDaysNames.size() - 1) {
                text.append(", ").append(checkedDaysNames.get(i));
            } else {
                text.append(" i ").append(checkedDaysNames.get(i));
            }
        }
        return text.toString();
    }

}
