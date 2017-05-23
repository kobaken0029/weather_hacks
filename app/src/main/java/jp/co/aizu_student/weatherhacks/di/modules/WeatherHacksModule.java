package jp.co.aizu_student.weatherhacks.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

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
import okhttp3.OkHttpClient;

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
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    WeatherHacksApiHelper provideWeatherHacksApiHelper(CompositeDisposable compositeDisposable) {
        return new WeatherHacksApiHelperImpl(compositeDisposable);
    }

    @Provides
    @Singleton
    WeatherHacksRssHelper provideWeatherHacksRssHelper(CompositeDisposable compositeDisposable) {
        return new WeatherHacksRssHelperImpl(compositeDisposable);
    }

    @Provides
    @Singleton
    TextToSpeechHelper provideTextToSpeechHelper() {
        return new TextToSpeechHelperImpl();
    }
}
