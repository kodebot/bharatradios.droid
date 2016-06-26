package uk.co.qubitssolutions.bharatradios.services.data.radio;

import android.os.AsyncTask;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.Language;

public class LanguageDataAsyncTask extends AsyncTask<Void, Void, List<Language>> {
    private Callback callback;
    private LanguageDataService languageDataService;

    public LanguageDataAsyncTask(Callback callback) {
        this.callback = callback;
        languageDataService = new LanguageDataService();
    }

    @Override
    protected List<Language> doInBackground(Void... params) {
        return languageDataService.getLanguages();
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
