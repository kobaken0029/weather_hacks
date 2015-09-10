package jp.co.aizu_student.weatherhacks.helpers.impl;

import java.util.ArrayList;
import java.util.List;

import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.models.Location;


/**
 * Created by koba on 2015/09/10.
 */
public class WeatherHacksRssHelperImpl implements WeatherHacksRssHelper {

    @Override
    public List<Location> getLocations() {
        Location hokkaido = new Location();
        hokkaido.setPrefecture("北海道");
        hokkaido.setCity("函館");
        hokkaido.setId("017010");
        Location aomori = new Location();
        aomori.setPrefecture("青森");
        aomori.setCity("青森");
        aomori.setId("020010");
        Location niigata = new Location();
        niigata.setPrefecture("新潟");
        niigata.setCity("長岡");
        niigata.setId("150020");
        Location tokyo = new Location();
        tokyo.setPrefecture("東京");
        tokyo.setCity("渋谷");
        tokyo.setId("130010");

        List<Location> locations = new ArrayList<>();
        locations.add(hokkaido);
        locations.add(hokkaido);
        locations.add(hokkaido);
        locations.add(hokkaido);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        return locations;
    }
}
