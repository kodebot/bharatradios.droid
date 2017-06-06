package uk.co.qubitssolutions.bharatradios.app.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.activities.MainActivity;
import uk.co.qubitssolutions.bharatradios.app.adapters.RadioListRecyclerAdapter;
import uk.co.qubitssolutions.bharatradios.app.others.DividerItemDecoration;
import uk.co.qubitssolutions.bharatradios.app.viewholders.RadioListItemViewHolder;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.data.radio.RadioDataAsyncTask;

public class ContentListRadioFragment extends Fragment {

    private BharatRadiosApplication application;
    private ProgressBar progressBar;
    RecyclerView recyclerView;
    private Language language;
    private boolean pendingLoading;

    public ContentListRadioFragment() {
    }

    @SuppressLint("ValidFragment")
    public ContentListRadioFragment(Language language) {
        this.language = language;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_list_radio, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.radio_list_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_radio_list);
        application = (BharatRadiosApplication) getActivity().getApplication();

        if (this.language == null) {
            int langId = savedInstanceState.getInt("CURRENT_LANG");
            for (final Language lang : application.getLanguages()) {
                if (lang.getId() == langId) {
                    this.language = lang;
                    break;
                }
            }
        }

        if (this.pendingLoading) {
            this.pendingLoading = false;
            setupRadioList();
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (application != null && language !=null) {
                application.setCurrentLanguageId(language.getId());
                setupRadioList();
                this.pendingLoading = false;
            } else {
                this.pendingLoading = true;
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(this.language != null) { // not sure why this would be null?
            outState.putInt("CURRENT_LANG", this.language.getId());
            super.onSaveInstanceState(outState);
        }
    }

    /**********************************************************************************************/
    /**********************************PRIVATE METHODS*********************************************/
    /**********************************************************************************************/
    private void setupRadioList() {
        showProgressBar();
        application.setCurrentLanguageId(language.getId());
        final RadioDataAsyncTask asyncTask = new RadioDataAsyncTask(new RadioDataAsyncTask.Callback() {
            @Override
            public void run(List<Radio> radios) {
                hideProgressBar();
                if (radios.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContentListRadioFragment.this.getContext());
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

                application.setRadios(application.getCurrentLanguageId(), radios);
                setupRadioListView();
            }
        });

        asyncTask.execute(language);
    }

    private void setupRadioListView() {
        RadioListRecyclerAdapter recyclerAdapter = new RadioListRecyclerAdapter(application);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        recyclerView.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false));

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.refreshDrawableState();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.refreshDrawableState();
    }
}
