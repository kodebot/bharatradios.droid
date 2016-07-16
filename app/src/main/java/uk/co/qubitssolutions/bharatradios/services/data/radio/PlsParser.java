package uk.co.qubitssolutions.bharatradios.services.data.radio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class PlsParser {
    private BufferedReader reader;

    public List<String> getUrls(String urlString) {
        LinkedList<String> urls = new LinkedList<String>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    String streamUrl = parseLine(line);
                    if (streamUrl != null && !streamUrl.equals("")) {
                        urls.add(streamUrl);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        return urls;
    }

    private String parseLine(String line) {
        if (line == null) {
            return null;
        }
        String trimmed = line.trim();
        if (trimmed.indexOf("http") >= 0) {
            return trimmed.substring(trimmed.indexOf("http"));
        }
        return "";
    }
}
