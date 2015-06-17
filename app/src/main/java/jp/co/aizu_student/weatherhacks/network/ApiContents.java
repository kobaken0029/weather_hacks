package jp.co.aizu_student.weatherhacks.network;

/**
 * Created by koba on 2015/06/18.
 */
public class ApiContents {
    public static final String BASE_URL = "http://weather.livedoor.com/";
    public static final String API_URL = "forecast/webservice/json/v1?city=";
    public static final int HTTP_CONNECT_TIMEOUT = 6000; // milliseconds
    public static final int HTTP_READ_TIMEOUT = 10000; // milliseconds
    public static final int HTTP_GET = 0;
    public static final int HTTP_POST = 1;

    public static String RESPONSE_VALUE_SUCCESS = "success";
    public static String PARAM_VALUE_OFF = "0";
    public static String PARAM_VALUE_ON = "1";
}