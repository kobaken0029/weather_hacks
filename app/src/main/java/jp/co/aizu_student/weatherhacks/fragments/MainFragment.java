package jp.co.aizu_student.weatherhacks.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.ObjectGraph;
import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.models.Temperature;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.modules.WeatherHacksModule;
import jp.co.aizu_student.weatherhacks.views.adapters.AsyncLoaderImageView;
import jp.co.aizu_student.weatherhacks.views.adapters.MyPagerAdapter;


public class MainFragment extends Fragment {
    @Bind(R.id.weather_text)
    TextView mWeatherTextView;

    @Bind(R.id.pref_text)
    TextView mPrefTextView;

    @Bind(R.id.max_temperature_text)
    TextView mMaxTempTextView;

    @Bind(R.id.min_temperature_text)
    TextView mMinTempTextView;

    @Bind(R.id.weather_image)
    AsyncLoaderImageView mImageView;

    @Inject
    WeatherHacksApiHelper apiHelper;

    @OnClick(R.id.pref_text)
    void onClickPrefText() {
        Intent intent = new Intent(getActivity(), LocationListActivity.class);
        getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectModule();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String param = MyApplication.newInstance().getLocationId();
        apiHelper.requestWeather(param, getActivity().getSupportFragmentManager());
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
        Location location = info.getLocation();
        Temperature temperature = forecast.getTemperature();

        String mPrefecture = location.getPrefecture() + " " + location.getCity();
        mPrefTextView.setText(mPrefecture);
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
        return getActivity().getString(msgId);
    }
}
