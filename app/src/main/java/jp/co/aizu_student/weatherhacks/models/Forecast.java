package jp.co.aizu_student.weatherhacks.models;

/**
 * Created by koba on 2015/09/06.
 */
public class Forecast {
    private String telop;
    private Temperature temperature;
    private WeatherImage image;

    public String getTelop() {
        return telop;
    }

    public void setTelop(String telop) {
        this.telop = telop;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public WeatherImage getImage() {
        return image;
    }

    public void setImage(WeatherImage image) {
        this.image = image;
    }
}
