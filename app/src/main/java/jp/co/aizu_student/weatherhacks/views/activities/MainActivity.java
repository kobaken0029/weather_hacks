package jp.co.aizu_student.weatherhacks.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import javax.inject.Inject;

import jp.co.aizu_student.weatherhacks.WeatherHacks;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.databinding.ActivityMainBinding;
import jp.co.aizu_student.weatherhacks.helpers.TextToSpeechHelper;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksApiHelper;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.adapter.MyPagerAdapter;

public class MainActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener {

    /**
     * リクエストコード
     */
    public static final int REQUEST_CODE = 1;

    /**
     * SharedPreferencesのKey
     */
    private static final String SHARED_PREFERENCES_KEY = "weather_hacks_app";
    private static final String SHARED_PREFERENCES_KEY_LOCATION_ID = "location_id";

    @Inject
    WeatherHacksApiHelper weatherHacksApiHelper;
    @Inject
    TextToSpeechHelper textToSpeechHelper;

    private ActivityMainBinding binding;

    private Toolbar.OnMenuItemClickListener mMenuItemClickListener = item -> {
        if (item.getItemId() == R.id.nationwide) {
            Intent intent = new Intent(this, LocationListActivity.class);
            startActivityForResult(intent, MainActivity.REQUEST_CODE);
        } else if (item.getItemId() == R.id.refresh) {
            Toast.makeText(MainActivity.this, getString(R.string.refresh_now), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() ->
                weatherHacksApiHelper.requestWeather(
                        WeatherHacks.getInstance().getLocationId(),
                        getSupportFragmentManager()
                ), 1500);
        } else if (item.getItemId() == R.id.voice_on_off) {
            // 音声ON/OFF切り替え
            textToSpeechHelper.toggleVoicePlay();

            // 音声再生の有無
            final boolean isPlayVoice = textToSpeechHelper.canPlayVoice();

            // Menu Iconの切り替え
            item.setIcon(isPlayVoice ? R.drawable.ic_mic_white_24dp : R.drawable.ic_mic_off_white_24dp);

            // メッセージを表示
            final String formatArg = isPlayVoice ? getString(R.string.on) : getString(R.string.off);
            final String message = getString(R.string.play_voice_switch_message, formatArg);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SharedPreferences data = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        WeatherHacks weatherHacks = WeatherHacks.getInstance();
        weatherHacks.setLocationId(data.getString(SHARED_PREFERENCES_KEY_LOCATION_ID, WeatherHacks.DEFAULT_LOCATION_ID));

        initToolbar(binding.toolbar, R.string.weather_info, false, true, mMenuItemClickListener);
        initTabLayout();
    }

    @Override
    protected void onDestroy() {
        weatherHacksApiHelper.onDestroy();
        weatherHacksApiHelper = null;
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String param = bundle.getString(Location.FIELD_NAME_ID);
                    weatherHacksApiHelper.requestWeather(param, getSupportFragmentManager());

                    WeatherHacks weatherHacks = WeatherHacks.getInstance();
                    weatherHacks.setLocationId(param);

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
        binding.viewPager.addOnPageChangeListener(this);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
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
}
