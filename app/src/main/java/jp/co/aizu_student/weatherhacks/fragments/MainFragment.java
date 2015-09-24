package jp.co.aizu_student.weatherhacks.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.ObjectGraph;
import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Temperature;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.modules.WeatherHacksModule;
import jp.co.aizu_student.weatherhacks.views.adapters.AsyncLoaderImageView;
import jp.co.aizu_student.weatherhacks.views.adapters.MyPagerAdapter;


public class MainFragment extends Fragment {
    private MainActivity mMainActivity;
    private TextView mWeatherTextView;
    private TextView mPrefTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private AsyncLoaderImageView mImageView;

    @Inject
    WeatherHacksApiHelper apiHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
        injectModule();
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
                Intent intent = new Intent(mMainActivity, LocationListActivity.class);
                mMainActivity.startActivityForResult(intent, MainActivity.REQUEST_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String param = MyApplication.newInstance().getLocationId();
        apiHelper.requestWeather(param, mMainActivity.getSupportFragmentManager());
    }

    /**
     * DIする。
     */
    private void injectModule() {
        ObjectGraph objectGraph = ObjectGraph.create(new WeatherHacksModule());
        objectGraph.inject(this);
    }

    /**
     * 天気情報から画面に値をセットする。
     *
     * @param info 天気情報
     */
    public void setViewFromWeatherInfo(WeatherInfo info) {
        Forecast forecast = info.getForecasts().get(getArguments().getInt(MyPagerAdapter.KEY_TARGET_DAY));
        Temperature temperature = forecast.getTemperature();

        mPrefTextView.setText(info.getLocation().getPrefecture() + " " + info.getLocation().getCity());
        mWeatherTextView.setText(forecast.getTelop());
        mMaxTempTextView.setText(temperature.getMax() != null
                ? temperature.getMax().get(Temperature.HASH_KEY_CELSIUS) + getMessage(R.string.celsius_symbol)
                : "");
        mMinTempTextView.setText(temperature.getMin() != null
                ? temperature.getMin().get(Temperature.HASH_KEY_CELSIUS) + getMessage(R.string.celsius_symbol)
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
        return mMainActivity.getString(msgId);
    }
}
