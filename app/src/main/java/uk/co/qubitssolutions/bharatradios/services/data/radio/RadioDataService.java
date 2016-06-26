package uk.co.qubitssolutions.bharatradios.services.data.radio;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.Radio;

public class RadioDataService {

    public ArrayList<Radio> getRadios(Language language) {
        ArrayList<Radio> radios = new ArrayList<>();
        try {
            JSONObject radioData = HttpJsonReader.read(language.getRadiosUrl());
            JSONArray radiosArray = radioData.getJSONArray("radios");

            int length = radiosArray.length();

            for (int i = 0; i < length; i++) {
                Radio radio = new Radio();
                JSONObject item = radiosArray.getJSONObject(i);
                radio.setId(item.getInt("id"));
                radio.setName(item.getString("name"));
                radio.setDescription(item.getString("description"));
                radio.setStreamUrl((item.getString("streamUrl")));
                radio.setSubtext(item.getString("subtext"));
                radio.setLanguageId(language.getId());
                radios.add(radio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return radios;
    }
}
