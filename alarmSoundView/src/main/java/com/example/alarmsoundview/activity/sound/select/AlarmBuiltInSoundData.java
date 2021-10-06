package com.example.alarmsoundview.activity.sound.select;

import com.example.alarmsoundview.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmBuiltInSoundData {
    private final List<BuiltInSound> sounds = new ArrayList<>();
    private int markedPosition = 0;

    public AlarmBuiltInSoundData(int soundId) {
        createSoundsList();
        checkCheckedSound(soundId);
    }

    private void createSoundsList() {
        sounds.add(new BuiltInSound("Pierwszy", R.raw.closer));
        sounds.add(new BuiltInSound("Drugi", R.raw.creep));
    }

    private void checkCheckedSound(int soundId) {
        findMarkedPosition(soundId);
        sounds.get(markedPosition).setChecked();
    }

    private void findMarkedPosition(int soundId) {
        if (soundId != 0) {
            findMarkedPositionComparingId(soundId);
        } else {
            markedPosition = 0;
        }
    }

    private void findMarkedPositionComparingId(int soundId) {
        for (int i = 0; i < sounds.size(); i++) {
            if (sounds.get(i).getId() == soundId) {
                markedPosition = i;
                break;
            }
        }
    }


    public int getSize() {
        return sounds.size();
    }

    public BuiltInSound get(int position) {
        return sounds.get(position);
    }

    public int getMarkedPosition() {
        return markedPosition;
    }

    public void setMarkedPosition(int markedPosition) {
        this.markedPosition = markedPosition;
    }
}
