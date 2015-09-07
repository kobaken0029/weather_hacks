package jp.co.aizu_student.weatherhacks.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.fragments.MainFragment;


public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    /** 今日 */
    private static final int TAB_TODAY = 0;

    /** タブの総数 */
    private static final int MAX_NUMBER_OF_TAB = 2;

    private AppCompatActivity mActivity;

    public MyPagerAdapter(AppCompatActivity activity, ViewPager viewPager) {
        super(activity.getSupportFragmentManager());
        mActivity = activity;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public Fragment getItem(final int position) {
        Log.d("ADAPTER", String.valueOf(position));
        Bundle bundle = new Bundle();
        bundle.putInt("targetDay", position);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return MAX_NUMBER_OF_TAB;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == TAB_TODAY
                ? mActivity.getString(R.string.today)
                : mActivity.getString(R.string.tomorrow);
    }
}