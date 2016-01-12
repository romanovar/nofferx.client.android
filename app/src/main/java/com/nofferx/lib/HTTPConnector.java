package com.nofferx.lib;

import android.renderscript.ScriptGroup;
import android.util.Xml;

import com.nofferx.helper.XMLParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rumi on 1/11/2016.
 */
public class HTTPConnector{

    private String urlString = "";
    public volatile boolean parsingComplete = true;

    public void fetchXML(final String param, XMLParser x){
        final String baseURL = "http://192.168.2.10:8080/com.nofferx.rest/rest/api/";
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
                    parse.callbackHistory(stream);


                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    parsingComplete = false;
                }
            }
        });
        thread.start();
    }
}