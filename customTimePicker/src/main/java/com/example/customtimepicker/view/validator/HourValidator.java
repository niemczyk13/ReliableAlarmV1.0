package com.example.customtimepicker.view.validator;

public class HourValidator {
    public static String checkTheCorrectnessOfTheEnteredHour(String hour) {
        if (oneCharacterWasEntered(hour)) {
            return blockTheEntryOfAnIncorrectFirstDigitOfTheHour(hour);
        } else if (moreThanOneCharacterHasBeenEntered(hour)) {
            return blockTheEntryOfAnIncorrectSecondDigitOfTheHour(hour);
        }
        return hour;
    }

    private static boolean oneCharacterWasEntered(String hour) {
        return hour.length() == 1;
    }

    private static boolean moreThanOneCharacterHasBeenEntered(String hour) {
        return hour.length() > 1;
    }

    private static String blockTheEntryOfAnIncorrectFirstDigitOfTheHour(String hour) {
        int h = Integer.parseInt(hour);
        if (h > 2 && h < 10) {
            String hReturn = "0" + hour;
            return hReturn;
        }
        return hour;
    }

    private static String blockTheEntryOfAnIncorrectSecondDigitOfTheHour(String hour) {
        int h = Integer.parseInt(hour);

        if (checkIf24IsEntered(h)) {
            return "00";
        }

        if (checkIfThereIsAnHourGreaterThan24(h)) {
            return "2";
        }

        return hour;
    }

    private static boolean checkIf24IsEntered(int hour) {
        return hour == 24;
    }

    private static boolean checkIfThereIsAnHourGreaterThan24(int hour) {
        return hour > 23;
    }

    public static String checkTheCorrectnessOfTheEnteredHourWhenHourChangFocus(String hour) {
        if (oneCharacterWasEntered(hour)) {
            String hReturn = "0" + hour;
            return hReturn;
        }
        return hour;
    }
}
