package uk.co.qubitssolutions.bharatradios.viewmodel;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.model.FavoriteLanguage;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteLanguagePreferenceService;

public class LanguageViewModel extends BaseObservable {

    private Language language;

    public LanguageViewModel(Language language) {
        this.language = language;
    }

    @Bindable
    public String getName() {
        return this.language.getName();
    }

    public void setName(String name) {
        this.language.setName(name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public boolean getSelected() {
        return this.language.getFavorite();
    }

    public void setSelected(boolean selected) {
        this.language.setFavorite(selected);
        notifyPropertyChanged(BR.selected);
    }

    public void onClick(View view) {
        setSelected(!getSelected());
        FavoriteLanguage fav = new FavoriteLanguage();
        fav.setLanguageId(language.getId());
        fav.setIsFavorite(language.getFavorite());
        FavoriteLanguagePreferenceService.getInstance(view.getContext()).update(fav);
    }
}
