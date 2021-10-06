package com.example.alarmsoundview.activity.sound.personal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alarmsoundview.R;

@RequiresApi(api = Build.VERSION_CODES.R)
public class PersonalSoundActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private MusicListAdapter adapter;
    private PlayButtonManager playButtonManager;
    private ActionBar actionBar;
    private String filter;

    @SuppressLint("NonConstantResourceId")
    //@BindView(R.id.sounds_list_view)
    ListView filesListView;

    @SuppressLint("NonConstantResourceId")
    //@BindView(R.id.sound_search_view)
    SearchView searchView;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_sound);
        ButterKnife.bind(this);
        filesListView = findViewById(R.id.sounds_list_view);
        searchView = findViewById(R.id.sound_search_view);

        actionBar = getSupportActionBar();
        addBackArrow();
        playButtonManager = new PlayButtonManager(this, cursor);
        //playButtonManager.setContext(getApplicationContext());
        //playButtonManager.setCursor(cursor);
        showMusicList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                filter = MediaStore.MediaColumns.TITLE + " LIKE '%" + query + "%' OR " + MediaStore.Audio.AlbumColumns.ARTIST + " LIKE '%" + query + "%'";
                LoaderManager.getInstance(PersonalSoundActivity.this).restartLoader(1, null, PersonalSoundActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter = MediaStore.MediaColumns.TITLE + " LIKE '%" + newText + "%' OR " + MediaStore.Audio.AlbumColumns.ARTIST + " LIKE '%" + newText + "%'";
                LoaderManager.getInstance(PersonalSoundActivity.this).restartLoader(1, null, PersonalSoundActivity.this);
                return false;
            }
        });


    }
    @SuppressLint("Range")
    private void showMusicList() {
        LoaderManager.getInstance(this).initLoader(1, null, this);

        filesListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long id) -> {
            adapter.stopMusic();
            cursor.moveToPosition(position);
            Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));

            String name = createName();

            Bundle bundle = new Bundle();
            bundle.putString("uri", uri.toString());
            bundle.putString("name", name);

            Intent intent = new Intent();
            intent.putExtra("data", bundle);
            setResult(RESULT_OK, intent);
            finish();

        });


    }

    @SuppressLint("Range")
    private String createName() {
        String author = getAuthor();
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE));
        if (author.length() != 0) {
            return author + " - " + title;
        } else {
            return title;
        }
    }

    @SuppressLint("Range")
    private String getAuthor() {
        String author = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
        if (author.equalsIgnoreCase("<unknown>")) {
            author = "";
        }
        return author;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adapter.stopMusic();
    }

    private void addBackArrow() {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        CursorLoader cl =  new CursorLoader(this, uri, null, filter, null, null);
        cl.setSortOrder(MediaStore.MediaColumns.TITLE + " ASC" );
        return cl;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        this.cursor = cursor;
        playButtonManager.setCursor(cursor);
        adapter = new MusicListAdapter(this, cursor, playButtonManager);
        filesListView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}