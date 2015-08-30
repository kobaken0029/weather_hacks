package jp.co.aizu_student.weatherhacks;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by koba on 2015/06/18.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;
    private static Context context;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static MyApplication newInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
            myApplication.setRequestQueue();
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        myApplication = new MyApplication();
        myApplication.setRequestQueue();
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

    /**
     * RequestQueue を取得する
     *
     * @return RequestQueue オブジェクト
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            setRequestQueue();
        }
        return mRequestQueue;
    }

    /**
     * ImageLoader を取得する
     *
     * @return ImageLoader オブジェクト
     */
    public ImageLoader getImageLoader() {
        if (mRequestQueue == null) {
            setRequestQueue();
        } else if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue, new ImageLruCache());
        }
        return mImageLoader;
    }

    /**
     * Volley の RequestQueue と ImageLoader を初期化する
     */
    private void setRequestQueue() {
        mRequestQueue = Volley.newRequestQueue(getContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLruCache());
    }

    /**
     * 画像のキャッシュを行う LruCache
     * ※ 参考: http://qiita.com/gari_jp/items/829a54bfa937f4733e29
     */
    public static class ImageLruCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mMemoryCache;

        public ImageLruCache() {
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            int cacheSize = maxMemory / 8;

            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mMemoryCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mMemoryCache.put(url, bitmap);
        }
    }
}
