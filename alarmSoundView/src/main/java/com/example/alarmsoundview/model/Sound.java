package com.example.alarmsoundview.model;

public class Sound {
    private int soundId = 0;
    private String uri = "";
    private String soundName = "";
    private boolean isPersonal = false;

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPersonal(boolean personal) {
        isPersonal = personal;
    }

    public void setSoundName(String name) {this.soundName = name;}

    public String getSoundName() {
        return soundName;
    }

    public String getUri() {
        return uri;
    }

    public boolean isPersonal() {
        return isPersonal;
    }
}
