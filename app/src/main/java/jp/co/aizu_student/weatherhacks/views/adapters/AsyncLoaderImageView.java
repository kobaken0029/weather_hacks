package jp.co.aizu_student.weatherhacks.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.AttributeSet;
import android.widget.ImageView;

import jp.co.aizu_student.weatherhacks.network.AsyncImageLoader;


/**
 * Created by koba on 2015/06/18.
 */
public class AsyncLoaderImageView extends ImageView
        implements LoaderManager.LoaderCallbacks<Bitmap> {
    private String url;

    public AsyncLoaderImageView(Context context) {
        super(context);
    }

    public AsyncLoaderImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncLoaderImageView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String url) {
        this.url = url;
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int i, Bundle bundle) {
        return new AsyncImageLoader(getContext(), this.url);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap bm) {
        setImageBitmap(bm);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> arg0) {
    }
}
