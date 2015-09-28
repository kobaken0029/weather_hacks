package jp.co.aizu_student.weatherhacks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import dagger.ObjectGraph;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.modules.WeatherHacksModule;


/**
 * Created by koba on 2015/09/10.
 */
public class BaseActivity extends AppCompatActivity {
    @Bind(R.id.toolbar_menu)
    Toolbar mToolbar;

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
    protected void initToolbar(int titleId, boolean isShowBackArrow, boolean isShowMenu,
                               Toolbar.OnMenuItemClickListener menuItemClickListener) {
        mToolbar.setTitle(titleId);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        if (isShowMenu) {
            mToolbar.inflateMenu(R.menu.main_menu);
            mToolbar.setOnMenuItemClickListener(menuItemClickListener);
        }

        if (isShowBackArrow) {
            mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

}
