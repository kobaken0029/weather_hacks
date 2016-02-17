package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.annimon.stream.Stream;
import com.google.gson.Gson;

import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherInfoHandler;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.network.ApiContents;

public class WeatherHacksApiHelperImpl implements WeatherHacksApiHelper {
    /** タグ */
    private static final String TAG = WeatherHacksApiHelper.class.getName();

    @Override
    public void requestWeather(String parameter, final FragmentManager fragmentManager) {
        MyApplication.newInstance().getRequestQueue().add(
                new JsonObjectRequest(
                        ApiContents.HTTP_GET,
                        ApiContents.BASE_URL + ApiContents.API_URL + parameter,
                        (String) null,
                        response -> {
                            WeatherInfo weatherInfo = new Gson().fromJson(response.toString(), WeatherInfo.class);
                            Stream.of(fragmentManager.getFragments()).forEach(handler ->
                                    ((WeatherInfoHandler) handler).setViewFromWeatherInfo(weatherInfo)
                            );
                        },
                        error -> Log.e(TAG, error.toString())
                )
        );
    }
}
