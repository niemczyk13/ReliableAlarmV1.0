package com.example.alarmsoundview.activity.sound.select;

import com.example.alarmsoundview.model.Sound;

public interface SelectSoundContractMVP {
    interface View {
        void updateListView();
        void onBackButtonPressed();
        void finishActivity(Sound sound);
    }

    interface Presenter {
        void itemClick(int position);
        void okButtonClick();
        void cancelButtonClick();
    }
}
