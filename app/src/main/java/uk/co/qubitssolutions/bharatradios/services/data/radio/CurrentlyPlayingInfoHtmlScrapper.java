package uk.co.qubitssolutions.bharatradios.services.data.radio;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.co.qubitssolutions.bharatradios.app.helpers.HttpHelper;

public class CurrentlyPlayingInfoHtmlScrapper {
    public static String getCurrentlyPlaying(String url) {
        try {
           url = HttpHelper.sanitizeUrl(url);

            Document document = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.113 Safari/537.36")
                    .get();
            Element elem = document.select("tr > td:contains(Current Song:)").get(0).nextElementSibling();
            return elem.text();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

}
