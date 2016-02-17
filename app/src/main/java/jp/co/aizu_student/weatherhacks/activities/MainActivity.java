package jp.co.aizu_student.weatherhacks.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import javax.inject.Inject;

import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.databinding.ActivityMainBinding;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import jp.co.aizu_student.weatherhacks.views.adapters.MyPagerAdapter;

public class MainActivity extends BaseActivity {
    /** リクエストコード */
    public static final int REQUEST_CODE = 1;

    /** SharedPreferencesのKey */
    private static final String SHARED_PREFERENCES_KEY = "weather_hacks_app";
    private static final String SHARED_PREFERENCES_KEY_LOCATION_ID = "location_id";

    @Inject
    WeatherHacksApiHelper apiHelper;

    private ActivityMainBinding binding;

    private Toolbar.OnMenuItemClickListener mMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.refresh) {
                String param = MyApplication.newInstance().getLocationId();

                if (TextUtils.isEmpty(param)) {
                    MyApplication.newInstance().setLocationId(ApiContents.PARAM_AIZU);
                }
                apiHelper.requestWeather(param, getSupportFragmentManager());

                Snackbar.make(binding.viewPager, getString(R.string.refresh_message), Snackbar.LENGTH_SHORT).show();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SharedPreferences data = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        MyApplication myApplication = MyApplication.newInstance();
        myApplication.setLocationId(data.getString(SHARED_PREFERENCES_KEY_LOCATION_ID, ApiContents.PARAM_AIZU));

        initToolbar(binding.toolbar, R.string.weather_info, false, true, mMenuItemClickListener);
        initTabLayout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String param = bundle.getString(Location.FIELD_NAME_ID);
                    apiHelper.requestWeather(param, getSupportFragmentManager());

                    MyApplication myApplication = MyApplication.newInstance();
                    myApplication.setLocationId(param);

                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(SHARED_PREFERENCES_KEY_LOCATION_ID, param);
                    editor.apply();
                }
                break;
            default:
                break;
        }
    }

    /**
     * tabの初期化。
     */
    private void initTabLayout() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.addOnPageChangeListener(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
