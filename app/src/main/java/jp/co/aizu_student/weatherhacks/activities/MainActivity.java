package jp.co.aizu_student.weatherhacks.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.aizu_student.weatherhacks.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ====Toolbar(Actionbar)を初期化==== */
        // ToolbarをLayoutファイルから取得

        // Toolbarのタイトルを設定

        // Toolbarのタイトル文字色を設定

        // ToolbarをSupportActionBarに設定

        /* ===================================== */


        /* =========タブレイアウトを初期化========= */
        // TabLayoutをLayoutファイルから取得

        // ViewPagerをLayoutファイルから取得

        // MyPagerAdapterを生成

        // ViewPagerにMyPagerAdapterを設定

        // ViewPagerに「ページが切り替わった時」のListenerを設定

        // TabLayoutにViewPagerを設定

        // TabLayoutのTabをいい感じに表示させるための設定(2行)

        /* ===================================== */
    }

    /**
     * ページャのアダプタークラス
     */
    private class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private AppCompatActivity mActivity;

        public MyPagerAdapter(AppCompatActivity activity) {
            super(activity.getSupportFragmentManager());
            mActivity = activity;
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
            /* ===表示するFragmentを生成＆返却=== */
            // Bundleを生成

            // BundleにFragment生成時に必要な値を設定(今日or明日)

            // Fragmentを生成

            // FragmentにBundleを設定

            // Fragmentを返却
            return null;

            /* =============================== */
        }

        @Override
        public int getCount() {
            // タブの表示個数を返却
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // タブに表示されるタイトルを返却
            return "";
        }
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
                // FragmentをMainFragmentにキャスト

                // Fragmentにあるリクエスト関数を呼び出す

            }

            // Snackbarで更新したことを知らせる

        }
        return super.onOptionsItemSelected(item);
    }
}
