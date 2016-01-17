package com.nofferx.parser;

import com.nofferx.lib.HTTPConnector;
import com.nofferx.models.ISubject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by lorenzo on 12-1-16.
 */
public class XMLGetRedeemedOfferParser implements XMLParser {

    private HTTPConnector hp;

    private ISubject h;


    public XMLGetRedeemedOfferParser(String email, int offset, int limit, ISubject h)
    {
        hp = new HTTPConnector();
        String httpRequest = "user/get/retrievedoffers/id="+email+"&limit="+limit+"&offset="+offset;
        this.h = h;
        hp.fetchXMLAsync(httpRequest, this);
    }
    /**
     *
     * @param s
     */
    public void callback(InputStream s){
        try{
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(s);
            doc.getDocumentElement().normalize();


            Element element = doc.getDocumentElement();
            NodeList nodes = element.getChildNodes();

            // Now accessing the historyResponse
            ArrayList<HashMap<String,String>> historyList = new ArrayList<>();

            // Loops through history and offer
            for(int i =0 ; i < nodes.getLength(); i++)
            {
                HashMap<String,String> ah = new HashMap<>();
                NodeList n  = nodes.item(i).getChildNodes();
                // Looping through the items
                for(int z = 0 ; z < n.getLength(); z ++)
                {
                    NodeList nl = n.item(z).getChildNodes();
                    for(int nn = 0; nn < nl.getLength(); nn++){
                        // Loops through history and offer
                        String k = nl.item(nn).getNodeName();
                        String v = nl.item(nn).getTextContent();
                        ah.put(k , v );
                    }
                }
                historyList.add(ah);
            }
            this.h.setState(historyList);
            // Set the states
        } catch(Exception e){

            System.out.println("Something went wrong in parsing the file | " + e.getMessage());

        }
    }


}
