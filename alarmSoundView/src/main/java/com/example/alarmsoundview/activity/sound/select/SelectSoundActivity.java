package com.example.alarmsoundview.activity.sound.select;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alarmsoundview.R;
import com.example.alarmsoundview.activity.sound.personal.PersonalSoundActivity;
import com.example.alarmsoundview.model.Sound;

public class SelectSoundActivity extends AppCompatActivity implements SelectSoundContractMVP.View {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Sound sound;
    private SelectSoundPresenter presenter;
    private SelectSoundAdapter adapter;
    private AlarmBuiltInSoundData data;
    @SuppressLint("NonConstantResourceId")
    //@BindView(R.id.built_in_sound_list_view)
    protected ListView soundListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sound);
        soundListView = findViewById(R.id.built_in_sound_list_view);
        addBackArrow();
        createSoundFromBundle();
        createAlarmBuiltInSoundData();
        createSelectSoundPresenter();
        createAdapterAndAddInListView();
        addOnItemClickListenerToFileListView();
        createActivityResultLauncher();
        addOnClickMethods();
    }


    private void createActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle bundle = addDataToBundle(result);
                        Intent intent = new Intent();
                        intent.putExtra("data", bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

        });
    }

    private Bundle addDataToBundle(ActivityResult result) {
        assert result.getData() != null;
        Bundle bundle = result.getData().getBundleExtra("data");
        bundle.putBoolean("is_personal", true);
        bundle.putInt("id", 0);
        return bundle;
    }

    private void addBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void createAlarmBuiltInSoundData() {
        data = new AlarmBuiltInSoundData(sound.getId());
    }

    private void createSoundFromBundle() {
        Bundle bundle = getIntent().getExtras().getBundle("data");
        sound = new Sound();
        sound.setId(bundle.getInt("id"));
        sound.setPersonal(bundle.getBoolean("is_personal"));
        sound.setUri(bundle.getString("uri"));
        sound.setName(bundle.getString("name"));
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
        bundle.putInt("id", sound.getId());
        bundle.putString("name", sound.getName());
        bundle.putString("uri", sound.getUri());
        bundle.putBoolean("is_personal", sound.isPersonal());

        Intent intent = new Intent();
        intent.putExtra("data", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void addOnClickMethods() {
        TextView addNewSound = findViewById(R.id.add_new_sound_text_view);
        addNewSound.setOnClickListener(this::addNewSoundTextViewClick);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this::saveButtonClick);

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this::cancelButtonClick);
    }

    public void addNewSoundTextViewClick(View view) {
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
        presenter.cancelButtonClick();
    }

}