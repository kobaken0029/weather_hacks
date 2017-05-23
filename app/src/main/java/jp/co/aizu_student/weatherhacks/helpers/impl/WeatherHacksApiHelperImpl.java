package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherInfoHandler;
import jp.co.aizu_student.weatherhacks.network.api.WeatherHacksApi;

public class WeatherHacksApiHelperImpl implements WeatherHacksApiHelper {
    /**
     * タグ
     */
    private static final String TAG = WeatherHacksApiHelper.class.getSimpleName();

    private final CompositeDisposable compositeDisposable;
    private final WeatherHacksApi weatherHacksApi;

    @Inject
    public WeatherHacksApiHelperImpl(CompositeDisposable compositeDisposable,
                                     WeatherHacksApi weatherHacksApi) {
        this.compositeDisposable = compositeDisposable;
        this.weatherHacksApi = weatherHacksApi;
    }

    @Override
    public void requestWeather(String parameter, final FragmentManager fragmentManager) {
        request(parameter, fragmentManager, false);
    }

    @Override
    public void refreshWeather(String parameter, FragmentManager fragmentManager) {
        request(parameter, fragmentManager, true);
    }

    private void request(String parameter, final FragmentManager fragmentManager, boolean isRefresh) {
        Disposable disposable = weatherHacksApi.get(parameter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weatherInfo -> {
                            WeatherInfoHandler handler = null;
                            for (Fragment f : fragmentManager.getFragments()) {
                                handler = ((WeatherInfoHandler) f);
                                handler.setViewFromWeatherInfo(weatherInfo);
                            }
                            if (handler != null) {
                                handler.showMessageForRefreshed(isRefresh);
                            }
                        },
                        throwable -> Log.e(TAG, throwable.toString())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
