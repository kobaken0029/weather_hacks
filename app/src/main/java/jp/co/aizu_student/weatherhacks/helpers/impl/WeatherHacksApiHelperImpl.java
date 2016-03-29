package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.annimon.stream.Stream;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherInfoHandler;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import jp.co.aizu_student.weatherhacks.network.api.WeatherHacksApi;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherHacksApiHelperImpl implements WeatherHacksApiHelper {
    /** タグ */
    private static final String TAG = WeatherHacksApiHelper.class.getSimpleName();

    private Subscription subscription;

    @Override
    public void requestWeather(String parameter, final FragmentManager fragmentManager) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiContents.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        subscription = retrofit.create(WeatherHacksApi.class).get(parameter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        weatherInfo -> Stream.of(fragmentManager.getFragments())
                                             .forEach(handler ->((WeatherInfoHandler) handler).setViewFromWeatherInfo(weatherInfo)),
                        throwable -> Log.e(TAG, throwable.toString()),
                        () -> Log.d(TAG, "onCompleted")
                );
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }
}
