package jp.co.aizu_student.weatherhacks.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.models.Temperature;

@BindingMethods({
        @BindingMethod(type = ListView.class, attribute = "android:onItemClick", method = "setOnItemClickListener"),
})
public final class BindingUtils {

    @BindingAdapter("temperature")
    public static void setTemperature(TextView textView, HashMap<String, String> temp) {
        String celsius = "";
        if (temp != null && temp.containsKey(Temperature.CELSIUS)) {
            celsius = textView.getContext().getString(R.string.celsius_symbol, temp.get(Temperature.CELSIUS));
        }
        textView.setText(celsius);
    }

    @BindingAdapter("location")
    public static void setLocation(TextView textView, Location location) {
        if (location == null) {
            textView.setText("");
            return;
        }

        String prefecture;
        String city;
        if (TextUtils.isEmpty(location.getPrefecture())) {
            prefecture = "";
        } else {
            prefecture = location.getPrefecture();
        }
        if (TextUtils.isEmpty(location.getCity())) {
            city = "";
        } else {
            city = location.getCity();
        }
        textView.setText(prefecture + " " + city);
    }
}
