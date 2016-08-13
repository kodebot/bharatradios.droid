package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.model.FavoriteLanguage;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteLanguagePreferenceService;

public class LanguageViewModel extends BaseObservable {
    private Language language;
    private Context context;
    private boolean favorite;

    public LanguageViewModel(Context context, Language language) {
        this.context = context;
        this.language = language;
        this.favorite = readFavorite();
    }

    public String getName() {
        return this.language.getName();
    }

    @Bindable
    public boolean isFavorite() {
        return favorite;
    }

    public void toggleFavorite(View view) {
        FavoriteLanguage fav = new FavoriteLanguage(this.language.getId(), this.isFavorite());
        FavoriteLanguagePreferenceService.getInstance(view.getContext()).update(fav);
        favorite = readFavorite();
        notifyPropertyChanged(BR.favorite);
    }

    private boolean readFavorite() {
        List<FavoriteLanguage> favoriteLanguages = FavoriteLanguagePreferenceService
                .getInstance(context)
                .getAll();
        for (FavoriteLanguage favoriteLanguage : favoriteLanguages) {
            if (favoriteLanguage.getLanguageId() == language.getId()) {
                return true;
            }
        }
        return false;
    }
}
