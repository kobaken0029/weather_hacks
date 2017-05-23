package jp.co.aizu_student.weatherhacks;

import android.app.Application;

import jp.co.aizu_student.weatherhacks.di.components.DaggerWeatherHacksComponent;
import jp.co.aizu_student.weatherhacks.di.components.WeatherHacksComponent;
import jp.co.aizu_student.weatherhacks.di.modules.WeatherHacksModule;

public class WeatherHacks extends Application {
    public static final String DEFAULT_LOCATION_ID = "070030";

    private String mLocationId;

    private WeatherHacksComponent weatherHacksComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        this.weatherHacksComponent = DaggerWeatherHacksComponent.builder()
                .weatherHacksModule(new WeatherHacksModule(this))
                .build();
    }

    public WeatherHacksComponent getWeatherHacksComponent() {
        return this.weatherHacksComponent;
    }

    public String getLocationId() {
        return mLocationId;
    }

    public void setLocationId(String mLocationId) {
        this.mLocationId = mLocationId;
    }
}
