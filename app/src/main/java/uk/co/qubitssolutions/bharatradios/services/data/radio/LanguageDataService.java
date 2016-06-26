package uk.co.qubitssolutions.bharatradios.services.data.radio;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.Radio;

public class LanguageDataService {

    public List<Language> getLanguages() {
        ArrayList<Language> languages = new ArrayList<>();
        try {
            JSONObject radioData = HttpJsonReader.read("https://raw.githubusercontent.com/vmanikandan001/Vaanoli/master/lang.json");
            JSONArray radiosArray = radioData.getJSONArray("languages");

            int length = radiosArray.length();

            for (int i = 0; i < length; i++) {
                Language language = new Language();
                JSONObject item = radiosArray.getJSONObject(i);
                language.setId(item.getInt("id"));
                language.setName(item.getString("name"));
                language.setRadiosUrl((item.getString("radiosUrl")));

                languages.add(language);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return languages;
    }
}
