package com.nofferx.lib;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rumi on 1/11/2016.
 */
public class HandleXML {
    private XmlPullParserFactory xmlFactoryObject;
    private String urlString = "";
    public volatile boolean parsingComplete = true;

    public HandleXML()
    {
        this.urlString = "";
    }
    public HandleXML(String url){
        this.urlString = url;
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
//                    URL url = new URL(urlString);
                    URL url = new URL("http://192.168.2.11:8080/com.nofferx.rest/rest/api/test");
                            //"user/register/email=3&password=3&tel=3");
                    HttpURLConnection conn = (HttpURLConnection)
                            url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES
                            , false);
                    myparser.setInput(stream, null);
     //               parseXMLAndStoreIt(myparser);
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
