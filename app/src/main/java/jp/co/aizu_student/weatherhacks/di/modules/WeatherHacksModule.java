package jp.co.aizu_student.weatherhacks.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import jp.co.aizu_student.weatherhacks.WeatherHacks;
import jp.co.aizu_student.weatherhacks.helpers.TextToSpeechHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.helpers.impl.TextToSpeechHelperImpl;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksApiHelperImpl;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksRssHelperImpl;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import jp.co.aizu_student.weatherhacks.network.RequestInterceptor;
import jp.co.aizu_student.weatherhacks.network.api.WeatherHacksApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Applicationのモジュール。
 */
@Module
public class WeatherHacksModule {
    private static final String SHARED_PREF_NAME = "weather_hacks_app";

    private final Context context;

    public WeatherHacksModule(WeatherHacks application) {
        this.context = application;
    }

    @Provides
    public Context provideContext() {
        return context.getApplicationContext();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    public Interceptor provideRequestInterceptor(RequestInterceptor interceptor) {
        return interceptor;
    }

    @Provides
    @Singleton
    WeatherHacksApi provideWeatherHacksApi() {
        return new Retrofit.Builder()
                .baseUrl(ApiContents.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(generateGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherHacksApi.class);
    }

    @Provides
    WeatherHacksApiHelper provideWeatherHacksApiHelper(CompositeDisposable compositeDisposable,
                                                       WeatherHacksApi weatherHacksApi) {
        return new WeatherHacksApiHelperImpl(compositeDisposable, weatherHacksApi);
    }

    @Provides
    WeatherHacksRssHelper provideWeatherHacksRssHelper(CompositeDisposable compositeDisposable,
                                                       OkHttpClient client) {
        return new WeatherHacksRssHelperImpl(compositeDisposable, client);
    }

    @Provides
    @Singleton
    TextToSpeechHelper provideTextToSpeechHelper(Context context) {
        return new TextToSpeechHelperImpl(context);
    }

    private Gson generateGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }
}
