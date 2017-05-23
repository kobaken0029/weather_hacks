package jp.co.aizu_student.weatherhacks.network.api;

import io.reactivex.Single;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherHacksApi {
    @GET(ApiContents.API_URL)
    Single<WeatherInfo> get(@Query("city") String city);
}
