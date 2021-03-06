package com.massivekinetics.ow.domain.tasks;

import android.os.AsyncTask;
import com.massivekinetics.ow.domain.Autocompleter;
import com.massivekinetics.ow.services.network.NetworkService;
import com.massivekinetics.ow.ui.interfaces.LoadingListener;

public class GetLocationFromPlaceTask extends AsyncTask<Void, Void, String> {

    private LoadingListener<String> listener;
    private String placeReference;
    private Autocompleter autocompleter;

    public GetLocationFromPlaceTask(String placeReference, Autocompleter autocompleter, LoadingListener<String> listener) {
        this.placeReference = placeReference;
        this.autocompleter = autocompleter;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.notifyStart();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (!NetworkService.isOnline() || placeReference == null)
            return null;
        String placeCoordinates = autocompleter.getPlaceCoordinates(placeReference);
        return placeCoordinates;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onLoaded(result);
        listener.notifyStop();
    }
}
