package com.example.rumi.client_nofferx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nofferx.models.HistorySubject;
import com.nofferx.models.IObserver;
import com.nofferx.parser.XMLGetHistoryParser;
import com.nofferx.parser.XMLGetOfferBusinessParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CompanyActivity extends AppCompatActivity implements IObserver {

    private HistorySubject historySubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        int id = intent.getIntExtra("offerId", -1);

        // Show Alert
        Toast.makeText(getApplicationContext(),
                "Loading..." + id , Toast.LENGTH_LONG)
                .show();

        this.historySubject = new HistorySubject();
        this.historySubject.attach(this);
        XMLGetOfferBusinessParser n = new XMLGetOfferBusinessParser(id, this.historySubject);
        ArrayList<HashMap<String,String>> list = this.historySubject.getHistoryList();
    }

    @Override
    public void update() {
        ArrayList<HashMap<String, String>> amp = this.historySubject.getHistoryList();
        System.out.println("Hello");
    }
}
