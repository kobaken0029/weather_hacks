package jp.co.aizu_student.weatherhacks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import dagger.ObjectGraph;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.modules.WeatherHacksModule;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectModule();
    }

    /**
     * DIする。
     */
    private void injectModule() {
        ObjectGraph objectGraph = ObjectGraph.create(new WeatherHacksModule());
        objectGraph.inject(this);
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
