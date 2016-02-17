package jp.co.aizu_student.weatherhacks.modules;

import dagger.Module;
import dagger.Provides;
import jp.co.aizu_student.weatherhacks.views.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.views.activities.MainActivity;
import jp.co.aizu_student.weatherhacks.views.fragments.MainFragment;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksApiHelperImpl;
import jp.co.aizu_student.weatherhacks.helpers.impl.WeatherHacksRssHelperImpl;

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
