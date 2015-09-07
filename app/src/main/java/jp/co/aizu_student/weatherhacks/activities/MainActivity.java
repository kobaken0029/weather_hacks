package jp.co.aizu_student.weatherhacks.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.views.adapters.MyPagerAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initTabLayout();
    }

    /**
     * toolbarの初期化。
     */
    protected void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_menu);

        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
    }

    /**
     * tabの初期化。
     */
    private void initTabLayout() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);

        new MyPagerAdapter(this, mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
