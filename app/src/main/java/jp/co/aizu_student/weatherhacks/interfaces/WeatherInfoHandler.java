package jp.co.aizu_student.weatherhacks.interfaces;

import android.view.View;

import jp.co.aizu_student.weatherhacks.models.WeatherInfo;

public interface WeatherInfoHandler {
    void setViewFromWeatherInfo(WeatherInfo info);
}
