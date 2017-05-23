package jp.co.aizu_student.weatherhacks.interfaces;

import jp.co.aizu_student.weatherhacks.models.WeatherInfo;

public interface UIWeatherHacksCallback {
    void showWeather(WeatherInfo info);
    void clearWeather();
}
