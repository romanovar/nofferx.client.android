package com.nofferx.lib;

import android.os.AsyncTask;



/**
 * Created by Rumi on 1/5/2016.
 */
class RequestTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        return  null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}
