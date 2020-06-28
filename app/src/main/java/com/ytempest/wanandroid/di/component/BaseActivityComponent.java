package com.ytempest.wanandroid.di.component;

import com.ytempest.wanandroid.base.activity.MvpActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<MvpActivity> {

    /**
     * 每一个继承于{@link MvpActivity}的Activity都继承于同一个子组件
     */
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MvpActivity> {

    }
}