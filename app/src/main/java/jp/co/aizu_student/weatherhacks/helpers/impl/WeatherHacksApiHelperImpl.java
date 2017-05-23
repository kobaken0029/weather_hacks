package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherInfoHandler;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import jp.co.aizu_student.weatherhacks.network.api.WeatherHacksApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherHacksApiHelperImpl implements WeatherHacksApiHelper {
    /** タグ */
    private static final String TAG = WeatherHacksApiHelper.class.getSimpleName();

    private final CompositeDisposable compositeDisposable;

    @Inject
    public WeatherHacksApiHelperImpl(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
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
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiContents.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Disposable disposable = retrofit.create(WeatherHacksApi.class).get(parameter)
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
