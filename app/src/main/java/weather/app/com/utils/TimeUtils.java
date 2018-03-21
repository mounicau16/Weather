package weather.app.com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by prasa on 3/20/2018.
 */

public class TimeUtils {
    public static String convertLongtoTimestamp(long millis){
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        String convertTimeStamp = sdf.format(calendar.getTime());
        return convertTimeStamp;
    }
}
