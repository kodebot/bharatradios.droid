package uk.co.qubitssolutions.bharatradios.services.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public static String read(Context context, String file, String key){
        SharedPreferences preferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void save(Context context, String file, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(file, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }


}
