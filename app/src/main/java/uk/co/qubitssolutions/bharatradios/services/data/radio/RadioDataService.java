package uk.co.qubitssolutions.bharatradios.services.data.radio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.model.Stream;

public class RadioDataService {

    public ArrayList<Radio> getRadios(Language language) {
        ArrayList<Radio> radios = new ArrayList<>();
        try {
            JSONObject radioData = HttpJsonReader.read(language.getRadiosUrl());
            JSONArray radiosArray = radioData.getJSONArray("radios");

            int length = radiosArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject item = radiosArray.getJSONObject(i);
                Radio radio = new Radio(
                        item.getInt("id"),
                        item.getString("name"),
                        item.getString("desc"),
                        item.getString("genre"),
                        language.getId());

                JSONArray streamArray = item.getJSONArray("streams");
                int streamsCount = streamArray.length();
                for (int j = 0; j < streamsCount; j++) {
                    JSONObject streamJson = streamArray.getJSONObject(j);
                    Stream stream = new Stream(streamJson.getString("src"),
                    streamJson.getString("srcName"),
                    streamJson.getInt("br"),
                    streamJson.getString("url"));
                    radio.addStream(stream);
                }
                radios.add(radio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return radios;
    }
}
