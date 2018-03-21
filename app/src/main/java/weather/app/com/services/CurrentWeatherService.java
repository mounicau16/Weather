package weather.app.com.services;

import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mounica on 3/18/2018.
 */

public class CurrentWeatherService {

    public  static Map<String, String> getCurrentWeather(String postalCode, String countryCode) {
       String url =  "http://api.openweathermap.org/data/2.5/weather?zip=" + postalCode +","+ countryCode + "&appid=c2ce07a83bcf842ad3da4d1f60865065";
        HttpURLConnection urlConnection = null;
        String response = "";
        StringBuilder parentData = new StringBuilder();

        try {
            URL object = new URL(url);
            urlConnection = (HttpURLConnection) object.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    parentData.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> currentWeather = parseCurrentWeatherJSON(parentData);
        return currentWeather;
    }

    public static Map<String, String> parseCurrentWeatherJSON(StringBuilder parentData) {
        Map<String, String> currentWeather = new HashMap<>();

        try {
            JSONObject parentJSONObj = new JSONObject(parentData.toString());

            JSONObject mainJSONObj = parentJSONObj.getJSONObject("main");
            String temp = mainJSONObj.getString("temp");
            String temp_min = mainJSONObj.getString("temp_min");
            String temp_max = mainJSONObj.getString("temp_max");

            JSONObject windJSONObj = parentJSONObj.getJSONObject("wind");
            String speed = windJSONObj.getString("speed");

            currentWeather.put("temp", temp);
            currentWeather.put("temp_min", temp_min);
            currentWeather.put("temp_max", temp_max);
            currentWeather.put("speed", speed);

        } catch (JSONException jsonexp) {
            jsonexp.printStackTrace();
        }
        return currentWeather;
    }
}
