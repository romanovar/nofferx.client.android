package com.nofferx.lib;

import com.nofferx.parser.XMLGetHistoryParser;
import com.nofferx.parser.XMLParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rumi on 1/11/2016.
 */
public class HTTPConnector{

    private String urlString = "";
    public volatile boolean parsingComplete = true;
    final String baseURL = "http://192.168.2.11:8080/com.nofferx.rest/rest/api/";

    public void fetchXMLAsync(final String param, XMLParser x){
        final XMLParser parse = x;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseURL + param);
                    HttpURLConnection conn = (HttpURLConnection)
                            url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    // Parse
                    parse.callback(stream);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle errors
                    parsingComplete = false;
                }
            }
        });
        thread.start();
    }

    public InputStream fetchXMLSync(final String param){
        InputStream stream = null;
        try {
            URL url = new URL(baseURL + param);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            stream = conn.getInputStream();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle errors
            parsingComplete = false;
        }
        return stream;
    }
}