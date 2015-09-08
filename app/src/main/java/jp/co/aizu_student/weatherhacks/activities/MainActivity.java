package jp.co.aizu_student.weatherhacks.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.fragments.MainFragment;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            for (Fragment f : getSupportFragmentManager().getFragments()) {
                MainFragment ff = (MainFragment) f;
                ff.requestToWeatherHacks(ApiContents.PARAM_AIZU);
            }
            Snackbar.make(findViewById(R.id.view_pager),
                    getString(R.string.refresh_message),
                    Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
