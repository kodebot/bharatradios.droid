package uk.co.qubitssolutions.bharatradios.services.data.favorite;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.FavoriteLanguage;
import uk.co.qubitssolutions.bharatradios.model.FavoriteRadio;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteLanguagePreferenceService;
import uk.co.qubitssolutions.bharatradios.services.preferences.FavoriteRadioPreferenceService;

public class FavoriteUtils {

    public static List<Radio> getFavRadios(Context context, List<Radio> allRadios) {
        List<FavoriteRadio> favRadios = FavoriteRadioPreferenceService.getInstance(context).getAll();
        List<Radio> radios = new ArrayList<>();
        for (FavoriteRadio favRadio : favRadios) {
            for (Radio radio : allRadios) {
                if (radio.getLanguageId() == favRadio.getLanguageId() &&
                        radio.getId() == favRadio.getRadioId()) {
                    radios.add(radio);
                }
            }
        }
        return radios;
    }

    public static List<Language> getFavLanguages(Context context, List<Language> allLanguages) {
        List<FavoriteLanguage> favLanguages = FavoriteLanguagePreferenceService.getInstance(context).getAll();
        List<Language> languages = new ArrayList<>();
        for (FavoriteLanguage favLanguage : favLanguages) {
            for (Language language : allLanguages) {
                if (language.getId() == favLanguage.getLanguageId()) {
                    languages.add(language);
                }
            }
        }
        return languages;
    }

}
