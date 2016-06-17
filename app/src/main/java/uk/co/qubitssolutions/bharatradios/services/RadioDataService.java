package uk.co.qubitssolutions.bharatradios.services;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import uk.co.qubitssolutions.bharatradios.model.Radio;

public class RadioDataService {
    public ArrayList<Radio> getRadios() {
        ArrayList<Radio> radios = new ArrayList<>();

        URL url;
        HttpsURLConnection connection = null;

        try {
            url = new URL("https://raw.githubusercontent.com/vmanikandan001/Vaanoli/master/list.json");
            connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
          String stringResult =  readStreamAsString(inputStream);

            JSONObject radioData = new JSONObject(stringResult);
            JSONArray radiosArray = radioData.getJSONArray("radios");

            int length = radiosArray.length();

            for(int i =0; i< length; i++){
                Radio radio = new Radio();
                JSONObject item = radiosArray.getJSONObject(i);
                radio.setId(item.getInt("id"));
                radio.setName(item.getString("name"));
                radio.setDescription(item.getString("description"));
                radio.setStreamUrl((item.getString("streamUrl")));
                radio.setSubtext(item.getString("subtext"));
                radios.add(radio);
            }

        } catch (MalformedURLException e) {
            Log.e("", "Invalid URL");
        } catch (IOException e) {
            Log.e("", "Connection error");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return radios;
    }

    private String readStreamAsString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
