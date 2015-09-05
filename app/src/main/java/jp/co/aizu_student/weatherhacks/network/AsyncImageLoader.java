package jp.co.aizu_student.weatherhacks.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by koba on 2015/06/18.
 */
public class AsyncImageLoader extends AsyncTaskLoader<Bitmap> {
    private String url;

    public AsyncImageLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public Bitmap loadInBackground() {
        Bitmap bmp = null;

        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            bmp = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }
}
