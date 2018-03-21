package weather.app.com.utils;

/**
 * Created by Mounica on 3/19/2018.
 */

public class WeatherUtils {

    public static int convertKelvinToFahrenheit(String kelvinTemp){
        int fahrenheitTemp = 0;
        Double kelvinTempNum = Double.valueOf(kelvinTemp);

        fahrenheitTemp=(int) (((kelvinTempNum - 273) * 9d/5) + 32);

        return fahrenheitTemp;
    }

}
