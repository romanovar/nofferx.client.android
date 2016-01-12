package com.example.rumi.client_nofferx;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class OrganizeDayOut extends AppCompatActivity {

    String radiusString;
    String budgetString;
    boolean smokingArea;
    boolean parkingArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_day_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.search_place);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUserValues();
                Snackbar.make(view, "Radius: " + radiusString+ " Budget: "+budgetString+" Smoking: "+smokingArea+ " Parking: "+ parkingArea, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void GetUserValues() {

        EditText radiusPlace = (EditText) findViewById(R.id.editText);
        EditText budget = (EditText) findViewById(R.id.editText2);
        CheckBox smoking = (CheckBox) findViewById(R.id.checkBox);
        CheckBox parking = (CheckBox) findViewById(R.id.checkBox3);

        radiusString = radiusPlace.getText().toString();
        budgetString = budget.getText().toString();
        smokingArea = false;
        parkingArea = false;

        if (smoking.isChecked()) {
            smokingArea = true;
        }
        if (parking.isChecked()) {
            parkingArea = true;
        }
    }

}
