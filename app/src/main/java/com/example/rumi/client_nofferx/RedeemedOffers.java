package com.example.rumi.client_nofferx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nofferx.models.HistorySubject;
import com.nofferx.models.IObserver;
import com.nofferx.parser.XMLGetHistoryParser;
import com.nofferx.parser.XMLGetRedeemedOfferParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RedeemedOffers extends AppCompatActivity implements IObserver {
    ListView listView;
    HistorySubject historySubject;

    // Defined Array values to show in ListView
    String[] values = new String[] {
            "Loading..."
    };
    ArrayList<String> value;
    ArrayList<String> codes;
    ArrayList<String> titles;
    ArrayAdapter<String> historyAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.history_list);
        codes = new ArrayList<>();
        value = new ArrayList<>();
        titles = new ArrayList<>();
        for(String i : values){
            value.add(i);
        }


        // Assign adapter to ListView
        historyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, value);


        listView.setAdapter(historyAdapter);




        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                if(itemValue.equals("Nothing to show")){ return; }
                // Get id of the offer
                int value = Integer.parseInt(itemValue.split("-")[0]);

                Intent myIntent;
                myIntent = new Intent( RedeemedOffers.this , QRCodeActivity.class);

                myIntent.putExtra("offerId", value); //Optional parameters
                myIntent.putExtra("title", titles.get(position));
                myIntent.putExtra("code", codes.get(position)); //Optional parameters

                RedeemedOffers.this.startActivity(myIntent);
            }

        });

        this.historySubject = new HistorySubject();
        this.historySubject.attach(this);
        // GET Email
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String userEmail = settings.getString("email", "");


        // TODO remove the following :
        userEmail = "lore@lore.lore";

        XMLGetRedeemedOfferParser n = new XMLGetRedeemedOfferParser( userEmail , 0, 1, this.historySubject);
        ArrayList<HashMap<String,String>> list = this.historySubject.getHistoryList();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void update() {


        ArrayList<String> offer = new ArrayList<>();

        ArrayList<HashMap<String,String>> hr =  this.historySubject.getHistoryList();
        codes.clear();

        String entry = "";
        for(int i = 0 ; i < hr.size(); i ++){

            String offerId = (String) hr.get(i).get("offer_idOffer");
            String time = (String) hr.get(i).get("endDate");
            String title = (String) hr.get(i).get("title");


            entry +=  offerId+"-" + "    " + time + "   "+ title;
            offer.add(entry);
            codes.add(hr.get(i).get("code"));
            titles.add(hr.get(i).get("title"));
        }
        value.clear();


        if(offer.size() > 0){
            for(String h : offer){
                value.add(h);
            }
        } else {
            value.add("Nothing to show");
        }
        historyAdapter.notifyDataSetChanged();
        listView.invalidate();
    }

}
