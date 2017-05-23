package jp.co.aizu_student.weatherhacks.helpers;

import java.util.List;

import jp.co.aizu_student.weatherhacks.interfaces.WeatherHacksCallback;
import jp.co.aizu_student.weatherhacks.models.Location;

public interface WeatherHacksRssHelper {
    void rssParse(WeatherHacksCallback<List<Location>> callback);
    void onDestroy();
}
