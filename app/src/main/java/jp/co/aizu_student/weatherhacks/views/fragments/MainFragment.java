package jp.co.aizu_student.weatherhacks.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.ObjectGraph;
import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.views.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.views.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.databinding.FragmentMainBinding;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherInfoHandler;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.models.Temperature;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.modules.WeatherHacksModule;
import jp.co.aizu_student.weatherhacks.adapter.MyPagerAdapter;

public class MainFragment extends Fragment implements WeatherInfoHandler {
    /** Bundle Key */
    private static final String KEY_TARGET_DAY = "target_day";

    @Inject
    WeatherHacksApiHelper apiHelper;

    private FragmentMainBinding binding;

    public static MainFragment newInstance(int position) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TARGET_DAY, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectModule();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        binding.setFragment(this);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String param = MyApplication.newInstance().getLocationId();
        apiHelper.requestWeather(param, getActivity().getSupportFragmentManager());
    }

    @Override
    public void onDestroy() {
        apiHelper.onDestroy();
        super.onDestroy();
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
    @Override
    public void setViewFromWeatherInfo(WeatherInfo info) {
        Forecast forecast = info.getForecasts().get(getArguments().getInt(MyPagerAdapter.KEY_TARGET_DAY));
        Location location = info.getLocation();
        Temperature temperature = forecast.getTemperature();

        binding.setForecast(forecast);
        binding.setLocation(location);
        binding.setTemperature(temperature);

        Picasso.with(getActivity())
                .load(forecast.getImage().getUrl())
                .fit()
                .error(R.drawable.no_image)
                .into(binding.weatherImage);
    }

    /**
     * IDからメッセージを取得する。
     *
     * @param msgId メッセージID
     * @return メッセージ
     */
    public String getMessage(int msgId) {
        return getActivity().getString(msgId);
    }

    public void onClickPrefText(View v) {
        Intent intent = new Intent(getActivity(), LocationListActivity.class);
        getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE);
    }
}
