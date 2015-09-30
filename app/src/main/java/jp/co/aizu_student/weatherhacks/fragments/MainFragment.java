package jp.co.aizu_student.weatherhacks.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.models.Temperature;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.views.adapters.AsyncLoaderImageView;


public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getName();

    private Activity mActivity;
    private TextView mWeatherTextView;
    private TextView mPrefTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private AsyncLoaderImageView mImageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activityを設定
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // FragmentのViewをinflateする
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // 各Viewを取得
        mWeatherTextView = (TextView) view.findViewById(R.id.weather_text);
        mPrefTextView = (TextView) view.findViewById(R.id.pref_text);
        mMaxTempTextView = (TextView) view.findViewById(R.id.max_temperature_text);
        mMinTempTextView = (TextView) view.findViewById(R.id.min_temperature_text);
        mImageView = (AsyncLoaderImageView) view.findViewById(R.id.weather_image);

        // 親Viewを返却
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* ===WeatherHacksのAPIにリクエストを送り、レスポンス情報を画面に表示=== */

        requestToWeatherHacks("070030");

        /* ============================================================== */
    }

    /**
     * お天気APIから天気情報を取得するためのリクエストを投げる。
     *
     * @param param パラメータ
     */
    public void requestToWeatherHacks(String param) {
        // APIのURLを取得
        String url = "http://weather.livedoor.com/forecast/webservice/json/v1?city=" + param;

        // RequestQueueを取得
        RequestQueue mRequestQueue = Volley.newRequestQueue(mActivity);

        // レスポンスのListenerを生成
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /* ===responseから値を取り出し、画面にセット=== */
                // 天気情報をJsonからJavaオブジェクトにパース
                WeatherInfo weatherInfo = new Gson().fromJson(response.toString(), WeatherInfo.class);

                // 各種オブジェクトを天気情報から取得
                Location location = weatherInfo.getLocation();
                Forecast forecast = weatherInfo.getForecasts().get(getArguments().getInt("targetDay"));
                Temperature temperature = forecast.getTemperature();

                // 各Viewに値を設定
                String prefectureText = location.getPrefecture() + " " + location.getCity();
                mPrefTextView.setText(prefectureText);
                mWeatherTextView.setText(forecast.getTelop());

                String maxTemperatureText = temperature.getMax() != null
                        ? temperature.getMax().get("celsius") + mActivity.getString(R.string.celsius_symbol)
                        : "";
                mMaxTempTextView.setText(maxTemperatureText);

                String minTemperatureText = temperature.getMin() != null
                        ? temperature.getMin().get("celsius") + mActivity.getString(R.string.celsius_symbol)
                        : "";
                mMinTempTextView.setText(minTemperatureText);

                // 画像をカスタムView(ImageView)に設定
                mImageView.setImageUrl(forecast.getImage().getUrl());
                getLoaderManager().initLoader(0, null, mImageView).forceLoad();

                /* ======================================== */
            }
        };

        // レスポンスのErrorListenerを生成
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        };

        // リクエストを作成
        JsonObjectRequest request =
                new JsonObjectRequest(0, url, (String) null, listener, errorListener);

        // リクエストをRequestQueueに追加
        mRequestQueue.add(request);
    }
}
