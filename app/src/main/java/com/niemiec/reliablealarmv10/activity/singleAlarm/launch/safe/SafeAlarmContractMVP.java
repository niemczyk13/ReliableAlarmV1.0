package com.niemiec.reliablealarmv10.activity.singleAlarm.launch.safe;

public interface SafeAlarmContractMVP {
    interface View {
        void showAlarmClock(int hour, int minute);
        void showActualClock(int hour, int minute);
        void showBatteryPercentageInfo(String s);
        void closeActivity();
    }

    interface Presenter {
        void initView(long id);
        void onOkButtonClick();
    }
}
