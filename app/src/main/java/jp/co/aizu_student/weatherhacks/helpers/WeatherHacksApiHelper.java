package jp.co.aizu_student.weatherhacks.helpers;

import jp.co.aizu_student.weatherhacks.interfaces.WeatherHacksCallback;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;

public interface WeatherHacksApiHelper {
    void requestWeather(String parameter, WeatherHacksCallback<WeatherInfo> callback);
    void onDestroy();
}
