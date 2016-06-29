package uk.co.qubitssolutions.bharatradios.services.data.radio;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.FavoriteLanguage;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteLanguagePreferenceService;

public class LanguageDataAsyncTask extends AsyncTask<Void, Void, List<Language>> {
    private Context context;
    private Callback callback;
    private LanguageDataService languageDataService;

    public LanguageDataAsyncTask(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
        languageDataService = new LanguageDataService();
    }

    @Override
    protected List<Language> doInBackground(Void... params) {
        List<Language> languages = languageDataService.getLanguages();
        List<FavoriteLanguage> favoriteLanguages = FavoriteLanguagePreferenceService.getInstance(this.context).getAll();
        for (Language language : languages) {
            for (FavoriteLanguage favoriteLanguage : favoriteLanguages) {
                if (language.getId() == favoriteLanguage.getLanguageId()) {
                    language.setFavorite(favoriteLanguage.getIsFavorite());
                }
            }
        }
        return languages;
    }

    @Override
    protected void onPostExecute(List<Language> languages) {
        super.onPostExecute(languages);
        callback.run(languages);
    }

    public interface Callback {
        void run(List<Language> languages);
    }

}
