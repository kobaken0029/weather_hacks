package jp.co.aizu_student.weatherhacks.helpers.impl;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.co.aizu_student.weatherhacks.activities.LocationListActivity;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.models.Location;

public class WeatherHacksRssHelperImpl implements WeatherHacksRssHelper {
    /** タグ */
    private static final String TAG = WeatherHacksRssHelper.class.getName();

    /** RSSのURL */
    private static final String RSS_URL = "http://weather.livedoor.com/forecast/rss/primary_area.xml";

    /** RSSのタグ名 */
    private static final String RSS_TAG_NAME_PREFECTURE = "pref";
    private static final String RSS_TAG_NAME_CITY = "city";

    /** RSSのバリュー名 */
    private static final String RSS_VALUE_NAME_TITLE = "title";
    private static final String RSS_VALUE_NAME_ID = "id";

    private static List<Location> locations;

    @Override
    public void rssParse(final Activity activity) {
        AsyncTask<String, Integer, List<Location>> rssTask = new AsyncTask<String, Integer, List<Location>>() {
            @Override
            protected List<Location> doInBackground(String... params) {
                if (locations != null) {
                    return locations;
                }

                try {
                    URL url = new URL(params[0]);
                    InputStream is = url.openConnection().getInputStream();
                    locations = parse(is);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (XmlPullParserException e) {
                    Log.e(TAG, e.getMessage());
                }

                return locations;
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                if (activity instanceof LocationListActivity) {
                    ((LocationListActivity) activity).initLocationListView(locations);
                }
            }

            private List<Location> parse(InputStream is) throws IOException, XmlPullParserException {
                Location location;
                String pref = "";
                locations = new ArrayList<>();

                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setInput(is, null);
                int eventType = xmlPullParser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tag;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            tag = xmlPullParser.getName();
                            if (tag.equals(RSS_TAG_NAME_PREFECTURE)) {
                                pref = xmlPullParser.getAttributeValue(null, RSS_VALUE_NAME_TITLE);
                            } else if (tag.equals(RSS_TAG_NAME_CITY)) {
                                location = new Location();
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
        };

        rssTask.execute(RSS_URL);
    }
}
