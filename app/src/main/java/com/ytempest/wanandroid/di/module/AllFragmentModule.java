package com.ytempest.wanandroid.di.module;

import com.ytempest.wanandroid.activity.main.home.HomeFrag;
import com.ytempest.wanandroid.di.component.BaseFragmentComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author heqidu
 * @since 2020/6/30
 */
@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AllFragmentModule {

    @ContributesAndroidInjector(modules = FragmentModules.EmptyModule.class)
    abstract HomeFrag contributeHomeFragInjector();

}
