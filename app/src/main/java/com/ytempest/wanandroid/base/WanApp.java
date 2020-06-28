package com.ytempest.wanandroid.base;

import android.app.Activity;
import android.app.Application;

import com.ytempest.tool.ToolModule;
import com.ytempest.wanandroid.di.component.AppComponent;
import com.ytempest.wanandroid.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author heqidu
 * @since 2020/6/22
 */
public class WanApp extends Application implements HasActivityInjector {
    @Override
    public void onCreate() {
        super.onCreate();
        ToolModule.init(this);

        AppComponent appComponent = DaggerAppComponent.builder().build();
        appComponent.inject(this);
    }

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }
}
