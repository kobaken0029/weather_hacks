package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.fragments.MainFragment;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.network.ApiContents;


/**
 * Created by koba on 2015/09/10.
 */
public class WeatherHacksApiHelperImpl implements WeatherHacksApiHelper {
    /** タグ */
    private static final String TAG = WeatherHacksApiHelper.class.getName();

    @Override
    public void requestWeather(String parameter, final FragmentManager fragmentManager) {
        String url = ApiContents.BASE_URL + ApiContents.API_URL + parameter;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                WeatherInfo weatherInfo = new Gson().fromJson(response.toString(), WeatherInfo.class);
                for (Fragment f : fragmentManager.getFragments()) {
                    MainFragment targetFragment = (MainFragment) f;
                    targetFragment.setViewFromWeatherInfo(weatherInfo);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // エラーが発生した場合
                Log.d(TAG, error.toString());
            }
        };

        JsonObjectRequest request =
                new JsonObjectRequest(ApiContents.HTTP_GET, url, (String) null, listener, errorListener);

        MyApplication.newInstance().getRequestQueue().add(request);
    }
}
