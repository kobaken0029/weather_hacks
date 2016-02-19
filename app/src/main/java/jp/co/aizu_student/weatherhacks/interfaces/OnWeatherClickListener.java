package jp.co.aizu_student.weatherhacks.interfaces;

import android.view.View;

public interface OnWeatherClickListener {
    void onPrefectureClick(View view);
    void onTemperatureClick(View view);
    void onWeatherClick(View view);
}
