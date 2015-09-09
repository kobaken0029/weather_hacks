package jp.co.aizu_student.weatherhacks.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Temperature;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import jp.co.aizu_student.weatherhacks.views.adapters.AsyncLoaderImageView;


public class MainFragment extends Fragment {
    private Activity mActivity;
    private TextView mWeatherTextView;
    private TextView mPrefTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private AsyncLoaderImageView mImageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mWeatherTextView = (TextView) view.findViewById(R.id.weather_text);
        mPrefTextView = (TextView) view.findViewById(R.id.pref_text);
        mMaxTempTextView = (TextView) view.findViewById(R.id.max_temperature_text);
        mMinTempTextView = (TextView) view.findViewById(R.id.min_temperature_text);
        mImageView = (AsyncLoaderImageView) view.findViewById(R.id.weather_image);

        mPrefTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivityForResult(new Intent(mActivity, LocationListActivity.class), 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestToWeatherHacks(ApiContents.PARAM_AIZU);
    }

    /**
     * WeatherHacksのAPIに対してリクエストする。
     *
     * @param parameter パラメータ(対象の地域ID)
     */
    public void requestToWeatherHacks(String parameter) {
        String url = ApiContents.BASE_URL + ApiContents.API_URL + parameter;

        MyApplication.newInstance().getRequestQueue().add(
                new JsonObjectRequest(ApiContents.HTTP_GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WeatherInfo weatherInfo = new Gson().fromJson(response.toString(), WeatherInfo.class);
                        setViewFromWeatherInfo(weatherInfo);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // エラーが発生した場合
                        Log.d("VolleySample", error.toString());
                    }
                }));
    }

    /**
     * 天気情報から画面に値をセットする。
     *
     * @param info 天気情報
     */
    private void setViewFromWeatherInfo(WeatherInfo info) {
        Forecast forecast = info.getForecasts().get(getArguments().getInt("targetDay"));
        Temperature temperature = forecast.getTemperature();

        mPrefTextView.setText(info.getLocation().getPrefecture() + " " + info.getLocation().getCity());
        mWeatherTextView.setText(forecast.getTelop());
        mMaxTempTextView.setText(temperature.getMax() != null
                ? temperature.getMax().get("celsius") + getMessage(R.string.celsius_symbol)
                : "");
        mMinTempTextView.setText(temperature.getMin() != null
                ? temperature.getMin().get("celsius") + getMessage(R.string.celsius_symbol)
                : "");

        mImageView.setImageUrl(forecast.getImage().getUrl());
        getLoaderManager().restartLoader(0, null, mImageView).forceLoad();
    }

    /**
     * IDからメッセージを取得する。
     *
     * @param msgId メッセージID
     * @return メッセージ
     */
    private String getMessage(int msgId) {
        return mActivity.getString(msgId);
    }
}
