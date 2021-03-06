package com.massivekinetics.ow.domain.parser.geocoder;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.massivekinetics.ow.domain.parser.geocoder.GeocoderConstants.*;

public class GeocoderParser {
    private static final String TAG = "GeocoderParser";

    public Map<String, String> getLocationInfoMap(String json) {
        Map<String, String> locationMap = new HashMap<String, String>();
        if (!json.contains("error")) {
            try {
                JSONObject dataObject = new JSONObject(json);
                String status = dataObject.getString(STATUS);
                if (status.equalsIgnoreCase(OK)) {
                    JSONArray resultsArray = dataObject.getJSONArray(RESULTS);
                    for (int pos = 0; pos < resultsArray.length(); pos++) {
                        JSONObject addressObject = resultsArray.getJSONObject(pos);
                        JSONArray addressComponentsArray = addressObject.getJSONArray(ADDRESS_COMPONENTS);
                        for (int i = 0; i < addressComponentsArray.length(); i++){
                            JSONObject addressItem = addressComponentsArray.getJSONObject(i);
                            JSONArray typeArray = addressItem.getJSONArray(TYPES);
                            if(typeArray.getString(0).equalsIgnoreCase(LOCALITY)){
                                String locationName = addressItem.getString(LONG_NAME);
                                locationMap.put(LOCALITY, locationName);
                            }  if(typeArray.getString(0).equalsIgnoreCase(COUNTRY)) {
                                String locationCountry = addressItem.getString(LONG_NAME);
                                locationMap.put(COUNTRY, locationCountry);
                            }
                        }
                    }
                }
            } catch (JSONException jse) {
                Log.e(TAG, jse.getMessage());
            }
        }
        return locationMap;
    }
}
