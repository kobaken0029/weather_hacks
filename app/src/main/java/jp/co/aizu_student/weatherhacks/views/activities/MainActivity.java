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
    private static final String SHARED_PREF_LOCATION_ID = "location_id";

    @Inject
    WeatherHacksApiHelper weatherHacksApiHelper;
    @Inject
    TextToSpeechHelper textToSpeechHelper;
    @Inject
    SharedPreferences sharedPreferences;

    private ActivityMainBinding binding;
    private Runnable runnable;
    private Handler handler;

    private Toolbar.OnMenuItemClickListener mMenuItemClickListener = item -> {
        if (item.getItemId() == R.id.nationwide) {
            Intent intent = new Intent(this, LocationListActivity.class);
            startActivityForResult(intent, MainActivity.REQUEST_CODE);
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

        final String defaultLocationId = sharedPreferences.getString(SHARED_PREF_LOCATION_ID,
                WeatherHacks.DEFAULT_LOCATION_ID);
        ((WeatherHacks) getApplication()).setLocationId(defaultLocationId);

        textToSpeechHelper.init(getApplicationContext());

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

                    ((WeatherHacks) getApplication()).setLocationId(param);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SHARED_PREF_LOCATION_ID, param);
                    editor.apply();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initToolbar(Toolbar toolbar, int titleId, boolean isShowBackArrow, boolean isShowMenu,
                               Toolbar.OnMenuItemClickListener menuItemClickListener) {
        toolbar.setTitle(titleId);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        if (isShowMenu) {
            toolbar.inflateMenu(textToSpeechHelper.canPlayVoice() ? R.menu.main_menu : R.menu.main_menu_voice_off);
            toolbar.setOnMenuItemClickListener(menuItemClickListener);
        }

        if (isShowBackArrow) {
            toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);
            toolbar.setNavigationOnClickListener(v -> finish());
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
        binding.swipeRefresh.setColorSchemeResources(R.color.red600, R.color.green600,
                R.color.blue600, R.color.orange600);

        // 更新処理用ハンドラ
        handler = new Handler();

        // 更新処理は非同期で行う
        runnable = () -> {
            weatherHacksApiHelper.refreshWeather(
                    ((WeatherHacks) getApplication()).getLocationId(),
                    getSupportFragmentManager()
            );
            binding.swipeRefresh.setRefreshing(false);
        };
        binding.swipeRefresh.setOnRefreshListener(() -> {
            Toast.makeText(MainActivity.this, getString(R.string.refresh_now), Toast.LENGTH_SHORT).show();
            handler.postDelayed(runnable, 2000);
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 画面がスクロールされたら更新処理をキャンセルする
        handler.removeCallbacks(runnable);
        binding.swipeRefresh.setRefreshing(false);
        binding.swipeRefresh.setEnabled(false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // SwipeRefreshによるViewPagerジャック対策
        binding.swipeRefresh.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
    }

    @Override
    public void onPageSelected(int position) {
    }
}
