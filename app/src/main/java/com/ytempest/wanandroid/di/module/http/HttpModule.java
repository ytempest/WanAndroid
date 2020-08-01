package com.ytempest.wanandroid.di.module.http;

import com.ytempest.wanandroid.BuildConfig;
import com.ytempest.wanandroid.base.WanApp;
import com.ytempest.wanandroid.di.module.http.event.NetEventListener;
import com.ytempest.wanandroid.http.HttpApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author heqidu
 * @since 2020/7/4
 */
@Module
public class HttpModule {

    private static final long CACHE_SIZE = 50 * 1024 * 1024;

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // 添加网络请求监控
            builder.eventListenerFactory(NetEventListener.FACTORY);
            // 添加http日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

        // 添加缓存控制
        File cacheDir = WanApp.getApp().getCacheDir();
        Cache cache = new Cache(cacheDir, CACHE_SIZE);
        builder.cache(cache);
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        builder.addInterceptor(cacheInterceptor);
        builder.addNetworkInterceptor(cacheInterceptor);

        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }


    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttp) {
        return new Retrofit.Builder()
                .baseUrl(HttpApi.BASE_URL)
                .client(okHttp)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    HttpApi provideHttpApi(Retrofit retrofit) {
        return retrofit.create(HttpApi.class);
    }
}
