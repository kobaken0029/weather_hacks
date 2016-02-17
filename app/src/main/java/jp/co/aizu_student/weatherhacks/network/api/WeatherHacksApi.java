package jp.co.aizu_student.weatherhacks.network.api;

import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherHacksApi {
    @GET(ApiContents.API_URL)
    Observable<WeatherInfo> get(@Query("city") String city);
}
