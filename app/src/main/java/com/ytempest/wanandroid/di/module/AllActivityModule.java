package com.ytempest.wanandroid.di.module;

import com.ytempest.wanandroid.activity.main.MainActivity;
import com.ytempest.wanandroid.di.component.BaseActivityComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author heqidu
 * @since 2020/6/28
 */
@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AllActivityModule {

    @ContributesAndroidInjector(modules = Modules.MainModule.class)
    abstract MainActivity contributesMainActivityInjector();
}
