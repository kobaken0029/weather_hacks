package jp.co.aizu_student.weatherhacks.di.modules;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

@Module
public class HttpClientModule {
    private static final String CACHE_FILE_NAME = "okhttp.cache";

    private static final long MAX_CACHE_SIZE = 4 * 1024 * 1024;

    @Singleton
    @Provides
    public OkHttpClient provideHttpClient(Context context, Interceptor interceptor) {
        File cacheDir = new File(context.getCacheDir(), CACHE_FILE_NAME);
        Cache cache = new Cache(cacheDir, MAX_CACHE_SIZE);

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BODY);

        OkHttpClient.Builder c = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .addInterceptor(logger);
        return c.build();
    }
}
