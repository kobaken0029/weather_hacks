package jp.co.aizu_student.weatherhacks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.views.fragments.MainFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    /** タグ */
    private static final String TAG = MyPagerAdapter.class.getName();

    /** 今日 */
    private static final int TAB_TODAY = 0;

    /** タブの総数 */
    private static final int MAX_NUMBER_OF_TAB = 2;

    /** BundleのKey */
    public static final String KEY_TARGET_DAY = "target_day";

    private Context context;

    public MyPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
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
}