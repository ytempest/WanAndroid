package com.ytempest.wanandroid.base;

import android.app.Application;

import com.ytempest.tool.ToolModule;

/**
 * @author heqidu
 * @since 2020/6/22
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToolModule.init(this);
    }
}
