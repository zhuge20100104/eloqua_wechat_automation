package com.oracle.auto.web.utility;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public String getServerTimeFromUrl(String serverUrl) {
        Date remoteDate = null;
        URL url;
        URLConnection urlConnection;

        try {
            url = new URL(serverUrl);
            urlConnection = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) urlConnection;
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("User-agent", "spider");
            conn.connect();
            Map<String, List<String>> header = conn.getHeaderFields();
            for (String key : header.keySet()) {
                if (key != null && "Date".equals(key)) {
                    List<String> data = header.get(key);
                    String dateString = data.get(0);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                    remoteDate = sdf.parse(dateString);
                    break;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Check if the url is accessible: " + serverUrl + "\n" + ex);
        }
        assert remoteDate != null;
        return remoteDate.toString();
    }
}
