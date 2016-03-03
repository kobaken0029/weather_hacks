package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.util.Log;
import android.util.Xml;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.interfaces.LocationListHandler;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherHacksRssHelperImpl implements WeatherHacksRssHelper {
    /** タグ */
    private static final String TAG = WeatherHacksRssHelper.class.getName();

    /** RSSのタグ名 */
    private static final String RSS_TAG_NAME_PREFECTURE = "pref";
    private static final String RSS_TAG_NAME_CITY = "city";

    /** RSSのバリュー名 */
    private static final String RSS_VALUE_NAME_TITLE = "title";
    private static final String RSS_VALUE_NAME_ID = "id";

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Subscription subscription;

    @Override
    public void rssParse(final LocationListHandler handler) {
        subscription = fetchRss()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        xml -> {
                            try {
                                handler.setUpLocationListView(parse(xml));
                            } catch (IOException | XmlPullParserException e) {
                                Log.e(TAG, e.getMessage());
                                handler.showErrorMessage();
                            }
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage());
                            handler.showErrorMessage();
                        }
                );
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    private Single<String> fetchRss() {
        return Single.create(subscriber -> {
            try {
                Request request = new Request.Builder()
                        .url(ApiContents.BASE_URL + "/" + ApiContents.RSS_URL)
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                subscriber.onSuccess(response.body().string());
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }

    private List<Location> parse(String xml) throws IOException, XmlPullParserException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new ByteArrayInputStream(xml.getBytes("utf-8")), null);
        int eventType = xmlPullParser.getEventType();

        String pref = "";
        List<Location> locations = new ArrayList<>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String tag = xmlPullParser.getName();
                if (tag.equals(RSS_TAG_NAME_PREFECTURE)) {
                    pref = xmlPullParser.getAttributeValue(null, RSS_VALUE_NAME_TITLE);
                } else if (tag.equals(RSS_TAG_NAME_CITY)) {
                    Location location = new Location();
                    location.setPrefecture(pref);
                    location.setCity(xmlPullParser.getAttributeValue(null, RSS_VALUE_NAME_TITLE));
                    location.setId(xmlPullParser.getAttributeValue(null, RSS_VALUE_NAME_ID));
                    locations.add(location);
                }
            }
            eventType = xmlPullParser.next();
        }

        return locations;
    }
}
