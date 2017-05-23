package jp.co.aizu_student.weatherhacks.interfaces;

public interface WeatherHacksCallback<T> {
    void onSuccess(T data);
    void onError(Throwable error);
}
