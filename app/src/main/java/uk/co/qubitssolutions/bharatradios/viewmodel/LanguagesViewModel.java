package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.activities.MainActivity;
import uk.co.qubitssolutions.bharatradios.model.FavoriteLanguage;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteLanguagePreferenceService;

public class LanguagesViewModel extends BaseObservable {

    private final Context context;

    public LanguagesViewModel(Context context){
        this.context = context;
    }

    public void onDone(View view){
        if(hasFavorite()){
            view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
            return;
        }

        Snackbar.make(view, "Please choose your favorite languages", Snackbar.LENGTH_SHORT).show();
    }

    private boolean hasFavorite() {
       return FavoriteLanguagePreferenceService
                .getInstance(context)
                .getAll().size() > 0;
    }
}
