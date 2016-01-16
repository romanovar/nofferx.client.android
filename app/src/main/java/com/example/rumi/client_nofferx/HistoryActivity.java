package com.example.rumi.client_nofferx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nofferx.parser.XMLGetHistoryParser;
import com.nofferx.models.HistorySubject;
import com.nofferx.models.IObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements IObserver {
    ListView listView;
    HistorySubject historySubject;

    // Defined Array values to show in ListView
    String[] values = new String[] {
            "Loading..."
    };
    ArrayList<String> value;

    ArrayAdapter<String> historyAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.history_list);

        value = new ArrayList<>();
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
                myIntent = new Intent( HistoryActivity.this , CompanyActivity.class);

                myIntent.putExtra("offerId", value); //Optional parameters
                HistoryActivity.this.startActivity(myIntent);
            }

        });

        this.historySubject = new HistorySubject();
        this.historySubject.attach(this);

        // GET Email
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String userEmail = settings.getString("email", "");

        // TODO remove the following :
        userEmail = "lore@lore.lore";

        XMLGetHistoryParser n = new XMLGetHistoryParser( userEmail , 0, 1, this.historySubject);
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


        ArrayList<String> history = new ArrayList<>();

        ArrayList<HashMap<String,String>> hr =  this.historySubject.getHistoryList();

        String entry = "";
        for(int i = 0 ; i < hr.size(); i ++){

            String offerId = (String) hr.get(i).get("offer_id");
            String time = (String) hr.get(i).get("time");
            String title = (String) hr.get(i).get("title");

            entry +=  offerId+"-" + "    " + time + "   "+ title;
            history.add(entry);
        }
        value.clear();

        if(history.size() > 0){
            for(String h : history){
                value.add(h);
            }
        } else {
            value.add("Nothing to show");
        }

        historyAdapter.notifyDataSetChanged();
        listView.invalidate();
    }

}
