package uk.co.qubitssolutions.bharatradios.app.helpers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {

    public static boolean isAlive(String url) {
        try {
            url = sanitizeUrl(url);
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            if (response.code() == 200){
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String sanitizeUrl(String url) {
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        int slashslash = url.indexOf("//") + 2;
        url = url.substring(0, slashslash) + url.substring(slashslash, url.indexOf('/', slashslash));
        return url;
    }

}
