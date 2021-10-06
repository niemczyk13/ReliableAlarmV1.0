package com.example.customtimepicker.view.validator;

public class MinuteValidator {
    public static String checkTheCorrectnessOfTheEnteredMinute(String minute) {
        if (oneCharacterWasEntered(minute)) {
            return addZeroForTheAppropriateNumbers(minute);
        }
        return minute;

    }

    private static String addZeroForTheAppropriateNumbers(String minute) {
        int m = Integer.parseInt(minute);
        if (m > 5) {
            String rMinute = "0" + minute;
            return rMinute;
        }
        return minute;
    }

    private static boolean oneCharacterWasEntered(String s) {
        return s.length() == 1;
    }

    public static String checkTheCorrectnessOfTheEnteredMinuteWhenMinuteChangFocus(String minute) {
        if (oneCharacterWasEntered(minute)) {
            String rMinute = "0" + minute;
            return rMinute;
        }
        return minute;
    }
}
