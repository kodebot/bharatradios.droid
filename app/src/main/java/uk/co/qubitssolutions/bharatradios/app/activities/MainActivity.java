package uk.co.qubitssolutions.bharatradios.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.adapters.RecyclerAdapter;
import uk.co.qubitssolutions.bharatradios.app.services.BackgroundAudioPlayerService;
import uk.co.qubitssolutions.bharatradios.app.viewholders.RadioListViewHolder;
import uk.co.qubitssolutions.bharatradios.model.Constants;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.data.radio.RadioDataAsyncTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RadioListViewHolder.ActionListener,
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private BharatRadiosApplication application;
    private AppCompatImageButton playStopToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        application = (BharatRadiosApplication) getApplication();

        setSupportActionBar(toolbar);
        setupFab();
        setupDrawer(toolbar);
        setupNav();
        setupRadioList();
        setupPlayerControls();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the run bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle run bar item clicks here. The run bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_language) {
            // Handle the camera run
        } else if (id == R.id.nav_preference) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void run(String action) {
        switch (action) {
            case Constants.ACTION_PLAY:
                sendActionIntent(Constants.ACTION_PLAY);
                showStopIcon();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_radio_player_play_stop_toggle:
                onPlayStopToggleClick();
                break;
            case R.id.action_radio_player_previous:
                onPreviousClick();
                break;
            case R.id.action_radio_player_next:
                onNextClick();
                break;
            case R.id.action_radio_player_volume_mute_toggle:
                onVolumeMuteToggleClick();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        onVolumeChange((float) progress / 100);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**********************************************************************************************
     * ******************************* PRIVATE METHODS*********************************************
     **********************************************************************************************/
    private void setupFab() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        if (fab != null) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Snackbar.make(view, "Replace with your own run", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });
//        }
    }

    private void setupDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void setupNav() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupRadioList() {
        RadioDataAsyncTask asyncTask = new RadioDataAsyncTask(new RadioDataAsyncTask.Callback() {
            @Override
            public void run(List<Radio> radios) {

                application.getRadioData().setRadios(radios);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_radio_list);
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplication(), recyclerView.getContext(), MainActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
            }
        });
        asyncTask.execute();
    }

    private void setupPlayerControls() {
        playStopToggleButton = (AppCompatImageButton) findViewById(R.id.action_radio_player_play_stop_toggle);
        AppCompatImageButton previousButton = (AppCompatImageButton) findViewById(R.id.action_radio_player_previous);
        AppCompatImageButton nextButton = (AppCompatImageButton) findViewById(R.id.action_radio_player_next);
        AppCompatImageButton volumeMuteToggleButton = (AppCompatImageButton) findViewById(R.id.action_radio_player_volume_mute_toggle);
        SeekBar volumeChangeSeekBar = (SeekBar) findViewById(R.id.action_radio_player_volume_seek_bar);

        playStopToggleButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        volumeMuteToggleButton.setOnClickListener(this);
        volumeChangeSeekBar.setOnSeekBarChangeListener(this);
        volumeChangeSeekBar.setProgress((int) (application.getRadioData().getCurrentVolume() * 100));
    }

    private void onPlayStopToggleClick() {
        if (application.getRadioData().getIsCurrentlyPlaying()) {
            stop();
        } else {
            play();
        }
    }

    private void onPreviousClick() {
        application.getRadioData().moveToPreviousRadio();
        play();
    }

    private void onNextClick() {
        application.getRadioData().moveToNextRadio();
        play();
    }

    private void onVolumeMuteToggleClick() {
        if (application.getRadioData().getIsInMute()) {
            mute();
        } else {
            unmute();
        }
    }

    private void onVolumeChange(float volume) {
        changeVolume(volume);
    }

    private void stop() {
        sendActionIntent(Constants.ACTION_STOP);
        showPlayIcon();
    }

    private void play() {
        sendActionIntent(Constants.ACTION_PLAY);
        showStopIcon();
    }

    private void mute() {
        application.getRadioData().setInMute(true);
        sendActionIntent(Constants.ACTION_MUTE);
    }

    private void unmute() {
        application.getRadioData().setInMute(false);
        sendActionIntent(Constants.ACTION_UNMUTE);

    }

    private void changeVolume(float volume) {
        application.getRadioData().setCurrentVolume(volume);
        sendActionIntent(Constants.ACTION_SET_VOLUME);
    }

    private void sendActionIntent(String action) {
        Intent intent = new Intent(this, BackgroundAudioPlayerService.class);
        intent.putExtra(Constants.EXTRA_ACTION, action);
        startService(intent);
    }

    private Radio getCurrentRadio() {
        return application.getRadioData().getCurrentRadio();
    }

    private void showPlayIcon() {
        playStopToggleButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp_wrapped);
        playStopToggleButton.refreshDrawableState();
    }

    private void showStopIcon() {
        playStopToggleButton.setBackgroundResource(R.drawable.ic_stop_black_24dp_wrapped);
        playStopToggleButton.refreshDrawableState();
    }
}
