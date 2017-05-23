package jp.co.aizu_student.weatherhacks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.annimon.stream.IntStream;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.interfaces.UIWeatherHacksCallback;
import jp.co.aizu_student.weatherhacks.interfaces.WeatherHacksCallback;
import jp.co.aizu_student.weatherhacks.models.WeatherInfo;
import jp.co.aizu_student.weatherhacks.views.fragments.MainFragment;

public class MyPagerAdapter extends FragmentPagerAdapter implements WeatherHacksCallback<WeatherInfo> {
    /** タグ */
    private static final String TAG = MyPagerAdapter.class.getName();

    /** 今日 */
    private static final int TAB_TODAY = 0;

    /** タブの総数 */
    public static final int MAX_NUMBER_OF_TAB = 2;

    /** BundleのKey */
    public static final String KEY_TARGET_DAY = "target_day";

    private Context context;
    private FragmentManager fragmentManager;

    public MyPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return MAX_NUMBER_OF_TAB;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == TAB_TODAY
                ? context.getString(R.string.today)
                : context.getString(R.string.tomorrow);
    }

    @Override
    public void onSuccess(WeatherInfo data) {
        IntStream.range(0, MyPagerAdapter.MAX_NUMBER_OF_TAB)
                .forEach(i -> {
                    Fragment f = getFragmentByPosition(i);
                    if (f instanceof UIWeatherHacksCallback) {
                        ((UIWeatherHacksCallback) f).showWeather(data);
                    }
                });
    }

    @Override
    public void onError(Throwable error) {
        Log.e(TAG, "onError: ", error);
        clearWeather();
    }

    public void clearWeather() {
        IntStream.range(0, MyPagerAdapter.MAX_NUMBER_OF_TAB)
                .forEach(i -> {
                    Fragment f = getFragmentByPosition(i);
                    if (f instanceof UIWeatherHacksCallback) {
                        ((UIWeatherHacksCallback) f).clearWeather();
                    }
                });
    }

    public Fragment getFragmentByPosition(int position) {
        return fragmentManager.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + position);
    }
}