package jp.co.aizu_student.weatherhacks;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private static Context context;

    private String mLocationId;

    public static MyApplication newInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        myApplication = new MyApplication();
    }

    /**
     * Application Context を取得する
     *
     * @return Context オブジェクト
     */
    public Context getContext() {
        if (context == null) {
            context = myApplication.getApplicationContext();
        }
        return context;
    }

    public String getLocationId() {
        return mLocationId;
    }

    public void setLocationId(String mLocationId) {
        this.mLocationId = mLocationId;
    }
}
