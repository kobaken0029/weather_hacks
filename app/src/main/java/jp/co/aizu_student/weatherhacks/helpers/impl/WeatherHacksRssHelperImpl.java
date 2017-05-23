package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherHacksCallback;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherHacksRssHelperImpl implements WeatherHacksRssHelper {
    /** タグ */
    private static final String TAG = WeatherHacksRssHelper.class.getName();

    /** RSSのタグ名 */
    private static final String RSS_TAG_NAME_PREFECTURE = "pref";
    private static final String RSS_TAG_NAME_CITY = "city";

    /** RSSのバリュー名 */
    private static final String RSS_VALUE_NAME_TITLE = "title";
    private static final String RSS_VALUE_NAME_ID = "id";

    private final CompositeDisposable compositeDisposable;
    private final OkHttpClient okHttpClient;

    @Inject
    public WeatherHacksRssHelperImpl(CompositeDisposable compositeDisposable, OkHttpClient client) {
        this.compositeDisposable = compositeDisposable;
        this.okHttpClient = client;
    }

    @Override
    public void rssParse(WeatherHacksCallback<List<Location>> callback) {
        Disposable disposable = fetchRss()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        xml -> {
                            try {
                                callback.onSuccess(parse(xml));
                            } catch (IOException | XmlPullParserException e) {
                                Log.e(TAG, e.getMessage());
                                callback.onError(e);
                            }
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage());
                            callback.onError(throwable);
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }

    private Single<String> fetchRss() {
        return Single.create(subscriber -> {
            try {
                Request request = new Request.Builder()
                        .url(ApiContents.BASE_URL + "/" + ApiContents.RSS_URL)
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                subscriber.onSuccess(response.body().string());
            } catch (Exception e) {
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
