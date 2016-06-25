package uk.co.qubitssolutions.bharatradios.app.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
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
import android.widget.ProgressBar;

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
        View.OnClickListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private BharatRadiosApplication application;
    private FloatingActionButton playStopToggleButton;
    private CardView playerControls;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);
        playerControls = (CardView) findViewById(R.id.radio_player_controls);

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
    protected void onResume() {
        super.onResume();
        if(application.getRadioData().getIsCurrentlyPlaying()){
            showStopIcon();
        }else{
            showPlayIcon();
        }
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
        switch (id) {
            case R.id.menu_action_fav_toggle:
                toggleFav(item);
                return true;
            case R.id.menu_action_off_timer:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_language:
                // Handle the camera run
                break;
            case R.id.nav_preference:

                break;
            case R.id.nav_about:

                break;
            case R.id.nav_help:

                break;
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
        }
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
        showProgessBar();
        final RadioDataAsyncTask asyncTask = new RadioDataAsyncTask(new RadioDataAsyncTask.Callback() {
            @Override
            public void run(List<Radio> radios) {
                if (radios.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.message_radio_list_network_error)
                            .setTitle(R.string.message_title_radio_list_network_error)
                            .setNeutralButton(R.string.text_retry_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    setupRadioList();
                                }
                            }).show();
                }

                application.getRadioData().setRadios(radios);
                setupRadioListView();
            }
        });
        asyncTask.execute();
    }

    private void setupRadioListView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_radio_list);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplication(), recyclerView.getContext(), MainActivity.this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        hideProgessBar();
    }

    private void setupPlayerControls() {
        playStopToggleButton = (FloatingActionButton) findViewById(R.id.action_radio_player_play_stop_toggle);
        FloatingActionButton previousButton = (FloatingActionButton) findViewById(R.id.action_radio_player_previous);
        FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.action_radio_player_next);

        playStopToggleButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
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

    private void stop() {
        sendActionIntent(Constants.ACTION_STOP);
        showPlayIcon();
    }

    private void play() {
        sendActionIntent(Constants.ACTION_PLAY);
        showStopIcon();
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
        playStopToggleButton.setImageResource(R.drawable.ic_play_arrow_black_24dp_wrapped);
        playStopToggleButton.refreshDrawableState();
    }

    private void showStopIcon() {
        playStopToggleButton.setImageResource(R.drawable.ic_stop_black_24dp_wrapped);
        playStopToggleButton.refreshDrawableState();
    }

    private void showProgessBar(){
        progressBar.setVisibility(View.VISIBLE);
        playerControls.setVisibility(View.INVISIBLE);
        playerControls.refreshDrawableState();
    }

    private void hideProgessBar(){
        progressBar.setVisibility(View.INVISIBLE);
        playerControls.setVisibility(View.VISIBLE);
        playerControls.refreshDrawableState();
    }

    private void toggleFav(MenuItem item){
        this.application.getToolBarData().setIsFavoriteOnly(!this.application.getToolBarData().getIsFavoriteOnly());
        setupRadioListView();
        if (this.application.getToolBarData().getIsFavoriteOnly()) {
            item.setIcon(R.drawable.ic_favorite_white_24dp_wrapped);
        }else{
            item.setIcon(R.drawable.ic_favorite_border_white_24dp_wrapped);
        }
    }
}
