package jp.co.aizu_student.weatherhacks.modules;

import dagger.Module;
import dagger.Provides;
import jp.co.aizu_student.weatherhacks.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.fragments.MainFragment;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksApiHelperImpl;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksRssHelperImpl;


/**
 * Created by koba on 2015/09/10.
 */
@Module(injects = { MainActivity.class, MainFragment.class, LocationListActivity.class})
public class WeatherHacksModule {

    @Provides
    WeatherHacksApiHelper provideWeatherHacksApiHelper() {
        return new WeatherHacksApiHelperImpl();
    }

    @Provides
    WeatherHacksRssHelper provideWeatherHacksRssHelper() {
        return new WeatherHacksRssHelperImpl();
    }
}
