package jp.co.aizu_student.weatherhacks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import dagger.ObjectGraph;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.modules.WeatherHacksModule;


/**
 * Created by koba on 2015/09/10.
 */
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
    protected void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_menu);

        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
    }

}
