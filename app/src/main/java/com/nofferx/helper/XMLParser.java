package com.nofferx.helper;

import com.nofferx.lib.HTTPConnector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by lorenzo on 12-1-16.
 */
public class XMLParser {

    private HTTPConnector hp;

    public XMLParser(){
        hp = new HTTPConnector();
    }

    public void getUserHistory(String email, int offset, int limit)
    {
        String httpRequest = "user/gethistory/id="+email+"&limit="+limit+"&offset="+offset;
        hp.fetchXML(httpRequest, this);
    }

    public void organizeDayOut() {

    }

    /**
     *
     * @param s
     */
    public void callbackHistory(InputStream s){

        try{
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(s);
            doc.getDocumentElement().normalize();

            Element element = doc.getDocumentElement();
            NodeList nodes = element.getChildNodes();

            ArrayList<ArrayList<HashMap<String,String>>> historyList = new ArrayList<>();


            for(int i =0 ; i < nodes.getLength(); i++)
            {
                ArrayList<HashMap<String,String>> hr = new ArrayList<>();

                NodeList n  = nodes.item(i).getChildNodes();

                for(int z = 0 ; z < n.getLength(); z ++)
                {
                    HashMap<String,String> ah = new HashMap<>();
                    ah.put(n.item(z).getNodeName(), n.item(z).getNodeValue());
                    hr.add(ah);
                }
                historyList.add(hr);
            }
        } catch(Exception e){

            System.out.println("Something went wrong in parsing the file");

        }
    }
}
