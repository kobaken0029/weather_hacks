package jp.co.aizu_student.weatherhacks.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
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
    private TextView textView;
    private AsyncLoaderImageView imageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) view.findViewById(R.id.weather_text);
        imageView = (AsyncLoaderImageView) view.findViewById(R.id.weather_image);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = ApiContents.BASE_URL + ApiContents.API_URL + "070030";

        MyApplication.newInstance().getRequestQueue().add(
                new JsonObjectRequest(ApiContents.HTTP_GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 通信に成功した場合
                        Log.d("VolleySample", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("forecasts");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if (object.has("telop")) {
                                    textView.setText(object.get("telop").toString());
                                }

                                if (object.has("image")) {
                                    JSONObject jsonObject = object.getJSONObject("image");
                                    if (jsonObject.has("url")) {
                                        imageView.setImageUrl(jsonObject.get("url").toString());
                                        getLoaderManager().initLoader(0, null, imageView).forceLoad();
                                        break;
                                    }
                                }
                            }
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

}
