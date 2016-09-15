package jp.co.aizu_student.weatherhacks.helpers;

import android.content.Context;

import jp.co.aizu_student.weatherhacks.models.Forecast;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.models.Temperature;

public interface TextToSpeechHelper {
    void init(Context context);
    void onResume();
    void onPause();
    void onDestroy();
    void talk(String sentence);
    void talkWeather(Forecast forecast);
    void talkTemperature(Temperature temperature);
    void talkWeatherWithTemperature(String whatDay, Location location, Forecast forecast, Temperature temperature);
    void toggleVoicePlay();
    boolean canPlayVoice();
}
