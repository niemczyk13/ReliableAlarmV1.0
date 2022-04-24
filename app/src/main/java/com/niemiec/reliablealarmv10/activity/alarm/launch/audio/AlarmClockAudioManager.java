package com.niemiec.reliablealarmv10.activity.alarm.launch.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.alarmsoundview.model.Sound;

public class AlarmClockAudioManager {
    private Context context;
    private AudioManager audioManager;
    private MediaPlayer player;
    private int originalVolume;
    private boolean isRing = false;


    public AlarmClockAudioManager(Context context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void startAlarm(Sound sound, int volume) {
        isRing = true;
        createMediaPlayer(sound);
        setVolume(volume);
        startMusic();

    }

    private void createMediaPlayer(Sound sound) {
        if (sound.isPersonal())
            try {
                player = MediaPlayer.create(context, Uri.parse(sound.getUri()));
            } catch (RuntimeException exception) {
                player = MediaPlayer.create(context, com.example.alarmsoundview.R.raw.closer);
            }
        else
            player = MediaPlayer.create(context, sound.getSoundId());
    }

    private void setVolume(int volume) {
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = (float) volume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxVolume * percent), 0);
    }

    private void startMusic() {
        player.setLooping(true);
        player.start();
    }

    public void startRisingAlarm(Sound sound, int volume, long risingSoundTime) {
        isRing = true;
        createMediaPlayer(sound);
        setRisingVolume(volume, risingSoundTime);
        startMusic();
    }

    private void setRisingVolume(int volume, long risingSoundTime) {
        int customMaxVolume = calculateMaxVolume(volume);
        long breakTime = risingSoundTime / customMaxVolume;

        startThreadForRisingVolume(breakTime, customMaxVolume);
    }

    private int calculateMaxVolume(int volume) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = (float) volume / 100;
        return (int) (percent * maxVolume);
    }

    private void startThreadForRisingVolume(long breakTime, int customMaxVolume) {
        new Thread(() -> {
            try {
                int startVolume = 0;
                do {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ++startVolume, 0);
                    Thread.sleep(breakTime);
                } while (startVolume < customMaxVolume && isRing);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopAlarm() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
        player.stop();
        player.release();
        isRing = false;
    }
}
