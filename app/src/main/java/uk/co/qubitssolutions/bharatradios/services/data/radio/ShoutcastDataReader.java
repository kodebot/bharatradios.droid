package uk.co.qubitssolutions.bharatradios.services.data.radio;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShoutcastDataReader {


    private static final String SHOUTCAST_SEARCH_URL = "http://api.shoutcast.com/legacy/stationsearch?k=9k8Ex3mDfDIKP8dd&search=<key>&br=<br>";


    public static List<ShoutcastStation> searchStation(String key, int bitRate) {
        try {
            String bitRateString = "";
            if(bitRate != 0){
                bitRateString = String.valueOf(bitRate);
            }
            String url = SHOUTCAST_SEARCH_URL
                    .replaceAll("<key>", key)
                    .replaceAll("<br>", String.valueOf(bitRateString));

            InputStream in = downloadUrl(url);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readShoutcastStation(parser);

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<ShoutcastStation> readShoutcastStation(XmlPullParser parser) throws IOException, XmlPullParserException {
// stationlist
        // tunein
        // base
        // station
        // name
        // id
        // br
        // ct

        List<ShoutcastStation> stations = new ArrayList<>();

        String base = "/sbin/tunein-station.pls"; // default base
        parser.require(XmlPullParser.START_TAG, "", "stationlist");
        parser.next();
        String tuneIn = parser.getName();

        if (tuneIn.equalsIgnoreCase("tunein")) {
            base = parser.getAttributeValue(null, "base");
        }
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getName().equalsIgnoreCase("station")) {
                String name = parser.getAttributeValue(null, "name");
                String id = parser.getAttributeValue(null, "id");
                int br = Integer.parseInt(parser.getAttributeValue(null, "br"));
                String ct = parser.getAttributeValue(null, "ct");
                ShoutcastStation station = new ShoutcastStation(base, name, id, br, ct);
                stations.add(station);
            }
        }

        return stations;
    }

    private static InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        return conn.getInputStream();
    }

    public static class ShoutcastStation {
        public final String base;
        public final String name;
        public final String id;
        public final int br;
        public final String ct;

        public ShoutcastStation(String base, String name, String id, int br, String ct) {
            this.base = base;
            this.name = name;
            this.id = id;
            this.br = br;
            this.ct = ct;
        }
    }

}
