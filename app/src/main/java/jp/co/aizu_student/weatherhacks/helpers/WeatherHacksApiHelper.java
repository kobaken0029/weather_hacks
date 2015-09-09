package jp.co.aizu_student.weatherhacks.helpers;


import android.support.v4.app.FragmentManager;

/**
 * Created by koba on 2015/09/10.
 */
public interface WeatherHacksApiHelper {
    void requestWeather(String parameter, FragmentManager fragmentManager);
}
