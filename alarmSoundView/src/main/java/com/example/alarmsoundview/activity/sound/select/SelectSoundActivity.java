package com.example.alarmsoundview.activity.sound.select;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmsoundview.R;
import com.example.alarmsoundview.activity.sound.personal.PersonalSoundActivity;
import com.example.alarmsoundview.model.Sound;
import com.example.globals.enums.BundleNames;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class SelectSoundActivity extends AppCompatActivity implements SelectSoundContractMVP.View {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Sound sound;
    private SelectSoundPresenter presenter;
    private SelectSoundAdapter adapter;
    private AlarmBuiltInSoundData data;
    @SuppressLint("NonConstantResourceId")
    protected ListView soundListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sound);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //getWindow().setStatusBarColor(Color.BLACK);

        soundListView = findViewById(R.id.built_in_sound_list_view);
        addBackArrow();
        createSoundFromBundle();
        createAlarmBuiltInSoundData();
        createSelectSoundPresenter();
        createAdapterAndAddInListView();
        addOnItemClickListenerToFileListView();
        createActivityResultLauncher();
        addOnClickMethods();
        EdgeToEdge.enable(this);
    }


    private void createActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle bundle = addDataToBundle(result);
                        Intent intent = new Intent();
                        intent.putExtra(BundleNames.DATA.name(), bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(this, R.string.info_about_null_personal_sounds, Toast.LENGTH_LONG).show();
                    }

        });
    }

    private Bundle addDataToBundle(ActivityResult result) {
        assert result.getData() != null;
        Bundle bundle = result.getData().getBundleExtra(BundleNames.DATA.name());
        bundle.putBoolean(BundleNames.IS_PERSONAL.name(), true);
        bundle.putInt(BundleNames.SOUND_ID.name(), 0);
        return bundle;
    }

    private void addBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void createAlarmBuiltInSoundData() {
        data = new AlarmBuiltInSoundData(sound.getSoundId());
    }

    private void createSoundFromBundle() {
        Bundle bundle = getIntent().getExtras().getBundle(BundleNames.DATA.name());
        sound = new Sound();
        sound.setSoundId(bundle.getInt(BundleNames.SOUND_ID.name()));
        sound.setPersonal(bundle.getBoolean(BundleNames.IS_PERSONAL.name()));
        sound.setUri(bundle.getString(BundleNames.URI.name()));
        sound.setSoundName(bundle.getString(BundleNames.NAME.name()));
    }

    private void createSelectSoundPresenter() {
        presenter = new SelectSoundPresenter(this, data);
        presenter.attach(this);
    }

    private void createAdapterAndAddInListView() {
        adapter = new SelectSoundAdapter(this, data);
        soundListView.setAdapter(adapter);
    }

    private void addOnItemClickListenerToFileListView() {
        soundListView.setOnItemClickListener((adapterView, view, position, l) -> presenter.itemClick(position));
    }

    @Override
    public void updateListView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackButtonPressed() {
        super.onBackPressed();
    }

    @Override
    public void finishActivity(Sound sound) {
        this.sound = sound;

        Bundle bundle = new Bundle();
        bundle.putInt(BundleNames.SOUND_ID.name(), sound.getSoundId());
        bundle.putString(BundleNames.NAME.name(), sound.getSoundName());
        bundle.putString(BundleNames.URI.name(), sound.getUri());
        bundle.putBoolean(BundleNames.IS_PERSONAL.name(), sound.isPersonal());

        Intent intent = new Intent();
        intent.putExtra(BundleNames.DATA.name(), bundle);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void addOnClickMethods() {
        TextView addNewSound = findViewById(R.id.add_new_sound_text_view);
        addNewSound.setOnClickListener(this::addNewSoundClick);

        ExtendedFloatingActionButton addPersonalSoundButton = findViewById(R.id.add_personal_sound_button);
        addPersonalSoundButton.setOnClickListener(this::addNewSoundClick);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this::saveButtonClick);

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this::cancelButtonClick);
    }

    public void addNewSoundClick(View view) {
        Intent intent = new Intent(this, PersonalSoundActivity.class);
        activityResultLauncher.launch(intent);
    }

    public void saveButtonClick(View view) {
        presenter.okButtonClick();
    }

    public void cancelButtonClick(View view) {
        presenter.cancelButtonClick();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.cancelButtonClick();
    }

    @Override
    public void addMenuProvider(@NonNull MenuProvider provider, @NonNull LifecycleOwner owner, @NonNull Lifecycle.State state) {

    }
}