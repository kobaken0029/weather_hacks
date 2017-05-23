package jp.co.aizu_student.weatherhacks.helpers.impl;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherHacksCallback;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.network.api.WeatherHacksApi;

public class WeatherHacksApiHelperImpl implements WeatherHacksApiHelper {
    private final CompositeDisposable compositeDisposable;
    private final WeatherHacksApi weatherHacksApi;

    @Inject
    public WeatherHacksApiHelperImpl(CompositeDisposable compositeDisposable,
                                     WeatherHacksApi weatherHacksApi) {
        this.compositeDisposable = compositeDisposable;
        this.weatherHacksApi = weatherHacksApi;
    }

    @Override
    public void requestWeather(String parameter, WeatherHacksCallback<WeatherInfo> callback) {
        Disposable disposable = weatherHacksApi.get(parameter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, callback::onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
