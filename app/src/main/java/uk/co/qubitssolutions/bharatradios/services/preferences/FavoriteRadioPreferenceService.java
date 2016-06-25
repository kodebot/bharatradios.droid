package uk.co.qubitssolutions.bharatradios.services.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.model.FavoriteRadio;

public class FavoriteRadioPreferenceService {
    private static FavoriteRadioPreferenceService instance;
    private final String FAV_RADIOS_FILE = "bhr_fav_radios_file";
    private final String FAV_RADIOS_KEY = "bhr_fav_radios_key";
    private final Context context;
    private List<FavoriteRadio> favoriteRadios;

    public static FavoriteRadioPreferenceService getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteRadioPreferenceService(context);
        }

        return instance;
    }

    public FavoriteRadioPreferenceService(Context context) {
        this.context = context;
        getAll();
    }

    public void update(FavoriteRadio favRadio) {
        FavoriteRadio radioFoundInFav = null;

        for (FavoriteRadio fav : this.favoriteRadios) {
            if (fav.getLanguageId() == favRadio.getLanguageId() &&
                    fav.getRadioId() == favRadio.getRadioId()) {
                radioFoundInFav = fav;
            }
        }

        if (radioFoundInFav != null) { // radio is no longer fav
            this.favoriteRadios.remove(radioFoundInFav);
        } else { // radio is chosen as fav
            this.favoriteRadios.add(favRadio);
        }

        this.save(this.favoriteRadios);

    }

    public List<FavoriteRadio> getAll() {
        if (this.favoriteRadios == null) {
            this.favoriteRadios = read();
        }

        return this.favoriteRadios;
    }

    private List<FavoriteRadio> read() {

        SharedPreferences preferences = this.context.getSharedPreferences(FAV_RADIOS_FILE, Context.MODE_PRIVATE);
        String favRadios = preferences.getString(FAV_RADIOS_KEY, "");
        favoriteRadios = new ArrayList<>();
        for (String favRadio : favRadios.split("¬")) {
            String[] favRadioParts = favRadio.split(",");
            if(favRadioParts.length == 2) { // it is junk if the length is not 2
                FavoriteRadio fav = new FavoriteRadio();
                fav.setLanguageId(Integer.parseInt(favRadioParts[0]));
                fav.setRadioId(Integer.parseInt(favRadioParts[1]));
                favoriteRadios.add(fav);
            }
        }

        return favoriteRadios;
    }

    private void save(List<FavoriteRadio> favoriteRadios) {
        StringBuilder favRadiosString = new StringBuilder();

        for (FavoriteRadio favRadio : favoriteRadios) {
            favRadiosString.append(favRadio.getLanguageId());
            favRadiosString.append(",");
            favRadiosString.append(favRadio.getRadioId());
            favRadiosString.append("¬");
        }

        SharedPreferences.Editor editor = this.context.getSharedPreferences(FAV_RADIOS_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(FAV_RADIOS_KEY, favRadiosString.toString());
        editor.apply();
    }

}
