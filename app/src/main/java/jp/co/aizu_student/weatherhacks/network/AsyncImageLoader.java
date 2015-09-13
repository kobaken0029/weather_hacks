package jp.co.aizu_student.weatherhacks.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;


/**
 * Created by koba on 2015/06/18.
 */
public class AsyncImageLoader extends AsyncTaskLoader<Bitmap> {
    private static final String TAG = AsyncImageLoader.class.getName();
    private String url;

    public AsyncImageLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public Bitmap loadInBackground() {
        /* ===HTTP通信を行い画像ファイルを取得===*/
        // Bitmapを宣言
        Bitmap bmp = null;

        try {
            // URLを生成(例外発生の可能性アリ)
            URL url = new URL(this.url);

            // HttpURLConnectionを生成
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 接続する
            connection.connect();

            // BitmapFactoryでデコード
            bmp = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (SocketTimeoutException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        // 画像を返却
        return bmp;

        /* ==================================*/
    }
}
