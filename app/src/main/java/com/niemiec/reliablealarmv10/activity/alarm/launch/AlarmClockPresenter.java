package com.niemiec.reliablealarmv10.activity.alarm.launch;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private Context context;
    private AlarmDataBase alarmDataBase;
    private Alarm alarm;
    private AudioManager audioManager;
    private MediaPlayer player;
    private int originalVolume;

    public AlarmClockPresenter(Context context) {
        super();
        this.context = context;
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    public void initView(Long id) {
        alarm = alarmDataBase.getAlarm(id);
        System.out.println(alarm);
        showAlarmData();
        callUpAlarm();
    }

    private void showAlarmData() {
        int hour = alarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = alarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
        if (alarm.nap.isActive()) {
            view.showAlarmClockWithNap(hour, minute);
        } else {
            view.showAlarmClockWithoutNap(hour, minute);
        }
    }

    private void callUpAlarm() {
        //TODO
        //1. wywołanie dźwięku (jeżeli nie działa ścieżka to dźwięk domyślny), jeżeli narastający dźwięk to inaczej, głośność
        //2. wibracji
        turnOnTheAlarmSound();
        turnOnVibration();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (alarm.sound.isPersonal())
            player = MediaPlayer.create(context, Uri.parse(alarm.sound.getUri()));
        else
            player = MediaPlayer.create(context, alarm.sound.getSoundId());

        System.out.println("Alarm volume: " + alarm.volume + " original volume: " + originalVolume + " max: " + maxVolume);
        float percent = (float) alarm.volume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxVolume * percent), 0);
        //player.setVolume(100, 100);
        System.out.println("Alarm volume: " + percent + " original volume: " + originalVolume + " max: " + maxVolume);

        player.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int volume = 0;
                    while (volume < (int) ((maxVolume * percent))) {
                        Thread.sleep(500);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ((volume++) / (int) (maxVolume * percent)) , 0);

                    }
//                    for (int i = 0; i < (int) ((maxVolume * percent) * 100); i++) {
//                        Thread.sleep(500);
//                        //audioManager.
//                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ((i) / (int) (maxVolume * percent)) , 0);
//                        System.out.println("I: " + i);
//                        //player.setVolume((i / (int) (maxVolume * percent)) * 100,(i / (int) (maxVolume * percent)) * 100);
//                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //TODO Jeżeli muzyka się skończy to od początku
        /*player.setOnCompletionListener(mediaPlayer -> {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
        });*/
    }

    private void turnOnTheAlarmSound() {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (alarm.sound.isPersonal())
            player = MediaPlayer.create(context, Uri.parse(alarm.sound.getUri()));
        else
            player = MediaPlayer.create(context, alarm.sound.getSoundId());

        System.out.println("Alarm volume: " + alarm.volume + " original volume: " + originalVolume + " max: " + maxVolume);
        float percent = (float) alarm.volume / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxVolume * percent), 0);
        //player.setVolume(100, 100);
        System.out.println("Alarm volume: " + percent + " original volume: " + originalVolume + " max: " + maxVolume);

        player.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int volume = 0;
                    while (volume < (int) ((maxVolume * percent))) {
                        Thread.sleep(500);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ((volume++) / (int) (maxVolume * percent)) , 0);

                    }
//                    for (int i = 0; i < (int) ((maxVolume * percent) * 100); i++) {
//                        Thread.sleep(500);
//                        //audioManager.
//                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ((i) / (int) (maxVolume * percent)) , 0);
//                        System.out.println("I: " + i);
//                        //player.setVolume((i / (int) (maxVolume * percent)) * 100,(i / (int) (maxVolume * percent)) * 100);
//                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //TODO Jeżeli muzyka się skończy to od początku
        /*player.setOnCompletionListener(mediaPlayer -> {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
        });*/
    }


    private void turnOnVibration() {
    }

    @Override
    public void onNapButtonClick() {
        //TODO
        //1. wywołanie nowego alarmu
        //2. zamknięcie aktywności
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);

        player.stop();
        player.release();
    }

    @Override
    public void onTurnOffButtonClick() {
        //TODO
        //1. zaktualizowanie alarmu - jeżeli harmonogram to kolejna data i wywołanie alarmu
        // jeżeli pojedyńcza data to aktualizacja isActive na false w bazie danych
        // zamknięcie aktywności
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);

        player.stop();
        player.release();
    }
}
