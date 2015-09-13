package jp.co.aizu_student.weatherhacks.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import jp.co.aizu_student.weatherhacks.views.adapters.AsyncLoaderImageView;


public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getName();

    private Activity mActivity;
    private TextView mWeatherTextView;
    private TextView mPrefTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private AsyncLoaderImageView mImageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activityを設定

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // FragmentのViewをinflateする

        // 各Viewを取得

        // 親Viewを返却
        return null;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* ===WeatherHacksのAPIにリクエストを送り、レスポンス情報を画面に表示=== */
        // APIのURLを取得
        String url = "http://weather.livedoor.com/forecast/webservice/json/v1?city=" + "070030";

        // RequestQueueを取得

        // レスポンスのListenerを生成
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /* ===responseから値を取り出し、画面にセット=== */
                // 天気情報をJsonからJavaオブジェクトにパース

                // 各種オブジェクトを天気情報から取得

                // 各Viewに値を設定

                // 画像をカスタムView(ImageView)に設定

                /* ======================================== */
            }
        };

        // レスポンスのErrorListenerを生成
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        };

        // リクエストを作成

        // リクエストをRequestQueueに追加

        /* ============================================================== */
    }
}
