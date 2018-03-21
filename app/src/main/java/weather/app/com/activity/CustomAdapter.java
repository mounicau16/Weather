package weather.app.com.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import weather.app.com.utils.TimeUtils;
import weather.app.com.utils.WeatherUtils;

/**
 * Created by prasa on 3/20/2018.
 */

public class CustomAdapter extends ArrayAdapter<Map<String, String>> {
    ArrayList<Map<String, String>> forecastData = new ArrayList<>();

    public CustomAdapter(Context context,int resource, ArrayList<Map<String, String>> forecastData) {
        super(context,R.layout.listview_items, forecastData);
        this.forecastData = forecastData;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.listview_items, null);


        TextView date = (TextView) v.findViewById(R.id.label);
        TextView temp = (TextView) v.findViewById(R.id.labe2);
        TextView description = (TextView) v.findViewById(R.id.labe3);
        String time = forecastData.get(position).get("date");
        Long longTime = Long.parseLong(time);
        String convertLongtoTimestamp = TimeUtils.convertLongtoTimestamp(longTime* 1000);
        date.setText( convertLongtoTimestamp);
        int convertTemp = WeatherUtils.convertKelvinToFahrenheit(forecastData.get(position).get("temp"));
        temp.setText(""+convertTemp);
        description.setText(forecastData.get(position).get("description"));

        return v;

    }
}
