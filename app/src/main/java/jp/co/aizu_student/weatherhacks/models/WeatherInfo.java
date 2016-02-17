package jp.co.aizu_student.weatherhacks.models;

import java.util.List;

public class WeatherInfo {
    private List<Forecast> forecasts;
    private Location location;

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
