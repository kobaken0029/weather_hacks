package jp.co.aizu_student.weatherhacks.modules;


import dagger.Module;
import dagger.Provides;
import jp.co.aizu_student.weatherhacks.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.fragments.MainFragment;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksApiHelperImpl;

/**
 * Created by koba on 2015/09/10.
 */
@Module(injects = { MainActivity.class, MainFragment.class })
public class WeatherHacksModule {

    @Provides
    WeatherHacksApiHelper provideWeatherHacksApiHelper() {
        return new WeatherHacksApiHelperImpl();
    }

}
