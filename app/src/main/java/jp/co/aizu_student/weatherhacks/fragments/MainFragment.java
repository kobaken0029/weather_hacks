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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.co.aizu_student.weatherhacks.MyApplication;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.network.ApiContents;
import jp.co.aizu_student.weatherhacks.views.adapters.AsyncLoaderImageView;


public class MainFragment extends Fragment {
    private Activity mActivity;
    private TextView mWeatherTextView;
    private TextView mPrefTextView;
    private TextView mMaxTempTextView;
    private TextView mMinTempTextView;
    private AsyncLoaderImageView mImageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mWeatherTextView = (TextView) view.findViewById(R.id.weather_text);
        mPrefTextView = (TextView) view.findViewById(R.id.pref_text);
        mMaxTempTextView = (TextView) view.findViewById(R.id.max_temperature_text);
        mMinTempTextView = (TextView) view.findViewById(R.id.min_temperature_text);
        mImageView = (AsyncLoaderImageView) view.findViewById(R.id.weather_image);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = ApiContents.BASE_URL + ApiContents.API_URL + "130010";

        MyApplication.newInstance().getRequestQueue().add(
                new JsonObjectRequest(ApiContents.HTTP_GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setWeather(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // エラーが発生した場合
                        Log.d("VolleySample", error.toString());
                    }
                }));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * レスポンス情報から天気情報を読み込み、画面にセットする。
     *
     * @param response レスポンス
     * @throws JSONException 例外
     */
    private void setWeather(JSONObject response) throws JSONException {
        // 通信に成功した場合
        Log.d("VolleySample", response.toString());

        JSONObject locationObject = response.getJSONObject("location");
        if (hasPref(locationObject)) {
            mPrefTextView.setText(locationObject.get("prefecture").toString());
        }

        JSONArray jsonArray = response.getJSONArray("forecasts");
        setForecasts(jsonArray);
    }

    /**
     * 天気予報を画面にセットする。
     *
     * @param jsonArray JSONの配列
     * @throws JSONException 例外
     */
    private void setForecasts(JSONArray jsonArray) throws JSONException {
        int targetDay = getArguments().getInt("targetDay");
        JSONObject object = jsonArray.getJSONObject(targetDay);

        if (hasTelop(object)) {
            mWeatherTextView.setText(object.get("telop").toString());
        }

        if (hasTemp(object)) {
            JSONObject jsonObject = object.getJSONObject("temperature");
            JSONObject obj;
            if (hasTemp(jsonObject, "max")) {
                obj = jsonObject.getJSONObject("max");
                if (obj.has("celsius")) {
                    mMaxTempTextView.setText(obj.get("celsius").toString() + getMessage(R.string.celsius_symbol));
                }
            }

            if (hasTemp(jsonObject, "min")) {
                obj = jsonObject.getJSONObject("min");
                if (obj.has("celsius")) {
                    mMinTempTextView.setText(obj.get("celsius").toString() + getMessage(R.string.celsius_symbol));
                }
            }
        }

        if (hasImage(object)) {
            JSONObject jsonObject = object.getJSONObject("image");
            if (hasUrl(jsonObject)) {
                mImageView.setImageUrl(jsonObject.get("url").toString());
                getLoaderManager().initLoader(0, null, mImageView).forceLoad();
            }
        }
    }

    /**
     * IDからメッセージを取得する。
     *
     * @param msgId メッセージID
     * @return メッセージ
     */
    private String getMessage(int msgId) {
        return mActivity.getString(msgId);
    }

    /**
     * 対象のJSONObjectに都道府県が含まれているかどうかを取得する。
     *
     * @param target 対象のJSONObject
     * @return 含まれていたらtrue
     */
    private boolean hasPref(JSONObject target) {
        return target.has("prefecture");
    }

    /**
     * 対象のJSONObjectに天気が含まれているかどうかを取得する。
     *
     * @param target 対象のJSONObject
     * @return 含まれていたらtrue
     */
    private boolean hasTelop(JSONObject target) {
        return target.has("telop");
    }

    /**
     * 対象のJSONObjectに気温が含まれているかどうかを取得する。
     *
     * @param target 対象のJSONObject
     * @return 含まれていたらtrue
     */
    private boolean hasTemp(JSONObject target) {
        return target.has("temperature");
    }

    /**
     * 対象のJSONObjectに最高気温(最低気温)が含まれているかどうかを取得する。
     *
     * @param target 対象のJSONObject
     * @param status 最高気温(最低気温)
     * @return JSONObjectが最高気温(最低気温)が含まれていたらtrue
     */
    private boolean hasTemp(JSONObject target, String status) {
        return (status.equals("max") || status.equals("min")) &&
                target.has(status) &&
                !target.isNull(status);
    }

    /**
     * 対象のJSONObjectに写真が含まれているかどうかを取得する。
     *
     * @param target 対象のJSONObject
     * @return 含まれていたらtrue
     */
    private boolean hasImage(JSONObject target) {
        return target.has("image");
    }

    /**
     * 対象のJSONObjectにURLが含まれているかどうかを取得する。
     *
     * @param target 対象のJSONObject
     * @return 含まれていたらtrue
     */
    private boolean hasUrl(JSONObject target) {
        return target.has("url");
    }
}
