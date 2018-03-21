package weather.app.com.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import weather.app.com.activity.R;
import weather.app.com.services.CurrentWeatherService;
import weather.app.com.services.ForecastWeatherService;
import weather.app.com.utils.TimeUtils;
import weather.app.com.utils.WeatherUtils;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    double latitude;
    double longitude;
    double altitude;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CustomAdapter customAdapter;

    String countryCode, postalCode;

    public static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int INITIAL_REQUEST = 1337;

    TextView Current_Weather,txtview_temp_min,txtview_temp_max,txtview_temp_wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtview_temp_min = (TextView)findViewById(R.id.txtview_temp_min);
        txtview_temp_max = (TextView)findViewById(R.id.txtview_temp_max);
        txtview_temp_wind = (TextView)findViewById(R.id.txtview_temp_wind);
        ListView list = (ListView)findViewById(R.id.listview);

        getCurrentLocation();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        populatePostalCodeNCountryCode();

        Map<String, String> currentWeather = CurrentWeatherService.getCurrentWeather(postalCode,countryCode);
        int convertTemp = WeatherUtils.convertKelvinToFahrenheit(currentWeather.get("temp"));
        int temp_min =WeatherUtils.convertKelvinToFahrenheit(currentWeather.get("temp_min"));
        int temp_max =WeatherUtils.convertKelvinToFahrenheit(currentWeather.get("temp_max"));
        setTitle("Currently " + convertTemp);
        txtview_temp_min.setText("Low Temperature\t"  +   temp_min);
        txtview_temp_max.setText("High Temperature\t" +  temp_max);
        txtview_temp_wind.setText("Wind Speed\t\t"    +    currentWeather.get("speed"));

        ArrayList<Map<String, String>> forecastcurrentWeather = ForecastWeatherService.getForecastWeather(postalCode,countryCode);
        //ArrayList<String> items = new ArrayList<>();

       customAdapter = new CustomAdapter(this,R.layout.listview_items,forecastcurrentWeather);
        list.setAdapter(customAdapter);




    }

    public void getCurrentLocation(){
        try {
            if (!canAccessLocation()) {
                requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            }
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2,this);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

    }
    private boolean canAccessLocation() {
        return(PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void populatePostalCodeNCountryCode() {
        try
        {
            final Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 10);

            Address address = addressList.get(0);
            countryCode = address.getCountryCode();
            postalCode = address.getPostalCode();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
