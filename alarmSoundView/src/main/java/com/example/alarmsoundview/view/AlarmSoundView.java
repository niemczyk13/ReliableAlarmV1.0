package com.example.alarmsoundview.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.example.alarmsoundview.R;
import com.example.alarmsoundview.activity.sound.select.SelectSoundActivity;
import com.example.alarmsoundview.model.Sound;

public class AlarmSoundView extends LinearLayout {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Sound sound;

    private ComponentActivity componentActivity;
    private TextView description;
    private TextView soundName;

    public AlarmSoundView(Context context) {
        super(context);
        setProperties();
    }

    public AlarmSoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProperties();
    }

    public AlarmSoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProperties();
    }

    private void setProperties() {
        this.componentActivity = (ComponentActivity) super.getContext();
        setParamsToMainLinearLayout();
        createViews();
        addOnClickMethodToViews();
        addViewsToMainLinearLayout();
        createActivityResultLauncher();
    }

    private void createActivityResultLauncher() {
        activityResultLauncher = componentActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        getSoundFromActivityResult(result.getData().getBundleExtra("data"));
                    }
                });
    }

    private void getSoundFromActivityResult(Bundle data) {
        updateSoundName(data.getString("name"));
        sound = new Sound();
        sound.setId(data.getInt("id"));
        sound.setPersonal(data.getBoolean("is_personal", false));
        sound.setUri(data.getString("uri"));
        sound.setName(data.getString("sound_name"));
        System.out.println("Sound uri: " + data.getInt("id"));

    }

    private void updateSoundName(String name) {
        soundName.setText(name);
    }

    private void addViewsToMainLinearLayout() {
        super.addView(description);
        super.addView(soundName);
    }

    private void addOnClickMethodToViews() {
        super.setOnClickListener(this::onClick);
        description.setOnClickListener(this::onClick);
        soundName.setOnClickListener(this::onClick);
    }

    private void createViews() {
        AlarmSoundViewBuilder alarmSoundViewBuilder = new AlarmSoundViewBuilder(super.getContext());
        description = alarmSoundViewBuilder.getDescriptionTextView();
        soundName = alarmSoundViewBuilder.getNameTextView();
    }

    private void setParamsToMainLinearLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.setLayoutParams(params);
        super.setOrientation(HORIZONTAL);
    }

    private void onClick(View view) {
        Intent intent = new Intent(super.getContext(), SelectSoundActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", sound.getId());
        bundle.putString("uri", sound.getUri());
        bundle.putBoolean("is_personal", sound.isPersonal());
        bundle.putString("name", sound.getName());
        intent.putExtra("data", bundle);


        activityResultLauncher.launch(intent);
    }

    public void initialize(Sound sound) {
        this.sound = sound;
        updateView();
    }

    private void updateView() {
        description.setText(R.string.alarm_sound);
        soundName.setText(sound.getName());
    }

    public Sound getSound() {
        return sound;
    }
}
