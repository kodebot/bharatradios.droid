package uk.co.qubitssolutions.bharatradios.services.preferences;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.FavoriteLanguage;

public class FavoriteLanguagePreferenceService {
    private static FavoriteLanguagePreferenceService instance;
    private final String FAV_LANG_FILE = "bhr_fav_lang_file";
    private final String FAV_LANG_KEY = "bhr_fav_lang_key";
    private final Context context;
    private List<FavoriteLanguage> favoriteLanguages;

    public static FavoriteLanguagePreferenceService getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteLanguagePreferenceService(context);
        }
        return instance;
    }

    private FavoriteLanguagePreferenceService(Context context) {
        this.context = context;
        getAll();
    }

    public void update(FavoriteLanguage favLanguage) {
        FavoriteLanguage langFoundInFav = null;

        for (FavoriteLanguage fav : this.favoriteLanguages) {
            if (fav.getLanguageId() == favLanguage.getLanguageId()) {
                langFoundInFav = fav;
            }
        }

        if (langFoundInFav != null) { // lang is no longer fav
            this.favoriteLanguages.remove(langFoundInFav);
        } else { // lang is chosen as fav
            this.favoriteLanguages.add(favLanguage);
        }

        this.save(this.favoriteLanguages);
    }

    public List<FavoriteLanguage> getAll() {
        if (this.favoriteLanguages == null) {
            this.favoriteLanguages = read();
        }
        return this.favoriteLanguages;
    }

    private List<FavoriteLanguage> read() {
        String favRadios = PreferenceManager.read(this.context, FAV_LANG_FILE, FAV_LANG_KEY);
        favoriteLanguages = new ArrayList<>();
        for (String favLang : favRadios.split("¬")) {
            if (!TextUtils.isEmpty(favLang)) {
                FavoriteLanguage fav = new FavoriteLanguage();
                fav.setLanguageId(Integer.parseInt(favLang));
                fav.setIsFavorite(true);
                favoriteLanguages.add(fav);
            }
        }
        return favoriteLanguages;
    }

    private void save(List<FavoriteLanguage> favoriteLanguages) {
        StringBuilder favLanguagesString = new StringBuilder();

        for (FavoriteLanguage favLanguage : favoriteLanguages) {
            favLanguagesString.append(favLanguage.getLanguageId());
            favLanguagesString.append("¬");
        }

        PreferenceManager.save(this.context, FAV_LANG_FILE, FAV_LANG_KEY, favLanguagesString.toString());
    }

}
