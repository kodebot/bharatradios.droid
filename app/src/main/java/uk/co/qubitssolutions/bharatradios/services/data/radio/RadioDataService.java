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
                Radio radio = new Radio();
                JSONObject item = radiosArray.getJSONObject(i);
                radio.setId(item.getInt("id"));
                radio.setName(item.getString("name"));
                radio.setDesc(item.getString("desc"));
                radio.setGenre(item.getString("genre"));
                JSONArray streamArray = item.getJSONArray("streams");
                int streamsCount = streamArray.length();
                Stream[] streams = new Stream[streamsCount];
                radio.setStreams(streams);
                for(int j=0; j<streamsCount;j++){
                    JSONObject streamJson = streamArray.getJSONObject(j);
                    Stream stream = new Stream();
                    streams[j] = stream;
                    stream.setSrc(streamJson.getString("src"));
                    stream.setSrcName(streamJson.getString("srcName"));
                    stream.setBitRate(streamJson.getInt("br"));
                    stream.setUrl(streamJson.getString("url"));
                }
                radio.setLanguageId(language.getId());
                radios.add(radio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return radios;
    }
}
