package weather.app.com.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mounica on 3/19/2018.
 */

public class ForecastWeatherService {
    public  static ArrayList<Map<String, String>> getForecastWeather(String postalCode, String countryCode) {
        String url ="http://api.openweathermap.org/data/2.5/forecast?zip="+ postalCode +","+ countryCode +"&appid=c2ce07a83bcf842ad3da4d1f60865065";
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
        ArrayList<Map<String, String>> forecastWeather = parseForecastWeatherJSON(parentData);
        return forecastWeather;
    }



    public static  ArrayList<Map<String, String>> parseForecastWeatherJSON(StringBuilder parentData) {
        ArrayList<Map<String, String>> forecastWeather = new ArrayList<Map<String, String>>();


        try {
            JSONObject parentJSONObj = new JSONObject(parentData.toString());
            // Getting JSON Array node
            JSONArray list = parentJSONObj.getJSONArray("list");

            // Looping through Forecast Weather List
            for (int i = 0; i < list.length(); i++) {
                Map<String, String> mapForecastWeather = new HashMap<>();
                JSONObject listJSONObj = list.getJSONObject(i);
                //Date
                String date = listJSONObj.getString("dt");
                //Temperature
                JSONObject mainJSONObj = listJSONObj.getJSONObject("main");
                String temp = mainJSONObj.getString("temp");

                //Weather

                JSONArray weatherList = listJSONObj.getJSONArray("weather");
                JSONObject weatherJSONObj = weatherList.getJSONObject(0);
                String description = weatherJSONObj.getString("description");

                mapForecastWeather.put("date",date);
                mapForecastWeather.put("temp",temp);
                mapForecastWeather.put("description",description);
                forecastWeather.add(mapForecastWeather);
            }

        } catch (JSONException jsonexp) {
            jsonexp.printStackTrace();
        }
        return forecastWeather;
    }

}
