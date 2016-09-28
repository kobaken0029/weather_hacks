package jp.co.aizu_student.weatherhacks.helpers;

import android.support.v4.app.FragmentManager;

public interface WeatherHacksApiHelper {
    void refreshWeather(String parameter, FragmentManager fragmentManager);
    void requestWeather(String parameter, FragmentManager fragmentManager);
    void onDestroy();
}
