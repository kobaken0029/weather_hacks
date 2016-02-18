package jp.co.aizu_student.weatherhacks.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import jp.co.aizu_student.weatherhacks.WeatherHacks;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.di.components.WeatherHacksComponent;

public class BaseActivity extends AppCompatActivity {

    /**
     * アプリケーションコンポーネントを取得する。
     * @return アプリケーションコンポーネント
     */
    protected WeatherHacksComponent getApplicationComponent() {
        return ((WeatherHacks) getApplication()).getWeatherHacksComponent();
    }

    /**
     * toolbarの初期化。
     */
    protected void initToolbar(Toolbar toolbar, int titleId, boolean isShowBackArrow, boolean isShowMenu,
                               Toolbar.OnMenuItemClickListener menuItemClickListener) {
        toolbar.setTitle(titleId);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        if (isShowMenu) {
            toolbar.inflateMenu(R.menu.main_menu);
            toolbar.setOnMenuItemClickListener(menuItemClickListener);
        }

        if (isShowBackArrow) {
            toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

}
