package jp.co.aizu_student.weatherhacks.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import jp.co.aizu_student.weatherhacks.di.modules.AndroidModule;
import jp.co.aizu_student.weatherhacks.di.modules.HttpClientModule;
import jp.co.aizu_student.weatherhacks.di.modules.WeatherHacksModule;
import jp.co.aizu_student.weatherhacks.views.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.views.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.views.fragments.MainFragment;

/**
 * Applicationのコンポーネント。
 */
@Singleton
@Component(modules = {WeatherHacksModule.class, AndroidModule.class, HttpClientModule.class})
public interface WeatherHacksComponent {
    void inject(MainActivity activity);
    void inject(LocationListActivity activity);
    void inject(MainFragment fragment);

    Context context();
}
