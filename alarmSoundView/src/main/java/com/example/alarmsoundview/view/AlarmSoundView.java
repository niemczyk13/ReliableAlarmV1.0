package com.example.alarmsoundview.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
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
import androidx.annotation.RequiresApi;

import com.example.alarmsoundview.R;
import com.example.alarmsoundview.activity.sound.select.SelectSoundActivity;
import com.example.alarmsoundview.model.Sound;
import com.example.globals.enums.BundleNames;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

public class AlarmSoundView extends LinearLayout {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Sound sound;

    private ComponentActivity componentActivity;
    private TextView description;
    private TextView soundName;
    private TypedArray options;

    public AlarmSoundView(Context context) {
        super(context);
        init(null);
    }

    public AlarmSoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AlarmSoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.componentActivity = (ComponentActivity) super.getContext();
        if (attrs != null) options = getContext().obtainStyledAttributes(attrs, R.styleable.AlarmSoundView);

        setParamsToMainLinearLayout();
        createViews();
        addOnClickMethodToViews();
        addViewsToMainLinearLayout();
        createActivityResultLauncher();
        if (options != null) options.recycle();
    }

    private void createActivityResultLauncher() {
        activityResultLauncher = componentActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        getSoundFromActivityResult(result.getData().getBundleExtra(BundleNames.DATA.name()));
                    }
                });
    }

    private void getSoundFromActivityResult(Bundle data) {
        updateSoundName(data.getString(BundleNames.NAME.name()));
        sound = new Sound();
        sound.setSoundId(data.getInt(BundleNames.SOUND_ID.name()));
        sound.setPersonal(data.getBoolean(BundleNames.IS_PERSONAL.name(), false));
        sound.setUri(data.getString(BundleNames.URI.name()));
        sound.setSoundName(data.getString(BundleNames.NAME.name()));
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
        AlarmSoundViewBuilder alarmSoundViewBuilder = new AlarmSoundViewBuilder(super.getContext(), options);
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
        bundle.putInt(BundleNames.SOUND_ID.name(), sound.getSoundId());
        bundle.putString(BundleNames.URI.name(), sound.getUri());
        bundle.putBoolean(BundleNames.IS_PERSONAL.name(), sound.isPersonal());
        bundle.putString(BundleNames.NAME.name(), sound.getSoundName());
        intent.putExtra(BundleNames.DATA.name(), bundle);

        activityResultLauncher.launch(intent);
    }

    public void initialize(Sound sound) {
        this.sound = sound;
        updateView();
    }

    private void updateView() {
        //description.setText(R.string.alarm_sound);
        soundName.setText(sound.getSoundName());
    }

    public Sound getSound() {
        return sound;
    }
}
