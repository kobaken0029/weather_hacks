package jp.co.aizu_student.weatherhacks.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jp.co.aizu_student.weatherhacks.WeatherHacks;
import jp.co.aizu_student.weatherhacks.helpers.TextToSpeechHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.helpers.impl.TextToSpeechHelperImpl;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksApiHelperImpl;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksRssHelperImpl;

/**
 * Applicationのモジュール。
 */
@Module
public class WeatherHacksModule {
    private final WeatherHacks application;

    public WeatherHacksModule(WeatherHacks application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application.getApplicationContext();
    }

    @Provides
    @Singleton
    WeatherHacksApiHelper provideWeatherHacksApiHelper() {
        return new WeatherHacksApiHelperImpl();
    }

    @Provides
    @Singleton
    WeatherHacksRssHelper provideWeatherHacksRssHelper() {
        return new WeatherHacksRssHelperImpl();
    }

    @Provides
    @Singleton
    TextToSpeechHelper provideTextToSpeechHelper() {
        return new TextToSpeechHelperImpl();
    }
}
