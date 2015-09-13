package jp.co.aizu_student.weatherhacks.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;


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

        // URLを生成(例外発生の可能性アリ)

        // HttpURLConnectionを生成

        // 接続する

        // BitmapFactoryでデコード

        // 画像を返却
        return null;

        /* ==================================*/
    }
}
