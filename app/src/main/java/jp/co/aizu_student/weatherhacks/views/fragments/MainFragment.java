package jp.co.aizu_student.weatherhacks.views.fragments;

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

import jp.co.aizu_student.weatherhacks.WeatherHacks;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.views.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.views.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.databinding.FragmentMainBinding;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherInfoHandler;
import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.adapter.MyPagerAdapter;

public class MainFragment extends Fragment implements WeatherInfoHandler, View.OnClickListener {
    /** Bundle Key */
    private static final String KEY_TARGET_DAY = "target_day";

    @Inject
    WeatherHacksApiHelper weatherHacksApiHelper;

    private FragmentMainBinding binding;

    public static MainFragment newInstance(int position) {
        MainFragment fragment = new MainFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_TARGET_DAY, position);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        binding.setListener(this);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String param = WeatherHacks.getInstance().getLocationId();
        weatherHacksApiHelper.requestWeather(param, getActivity().getSupportFragmentManager());
    }

    @Override
    public void onDestroy() {
        weatherHacksApiHelper.onDestroy();
        weatherHacksApiHelper = null;
        super.onDestroy();
    }

    /**
     * DIする。
     */
    private void injectModule() {
        ((WeatherHacks) getActivity().getApplication()).getWeatherHacksComponent().inject(this);
    }

    /**
     * 天気情報から画面に値をセットする。
     *
     * @param info 天気情報
     */
    @Override
    public void setViewFromWeatherInfo(WeatherInfo info) {
        Forecast forecast = info.getForecasts().get(getArguments().getInt(MyPagerAdapter.KEY_TARGET_DAY));

        binding.setForecast(forecast);
        binding.setLocation(info.getLocation());
        binding.setTemp(forecast.getTemperature());

        Picasso.with(getActivity())
                .load(forecast.getImage().getUrl())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(binding.weatherImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), LocationListActivity.class);
        getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE);
    }
}
