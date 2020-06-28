package com.ytempest.wanandroid.di.module;


import com.ytempest.wanandroid.interactor.DbHelper;
import com.ytempest.wanandroid.interactor.HttpHelper;
import com.ytempest.wanandroid.interactor.PreferencesHelper;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.interactor.impl.DbHelperImpl;
import com.ytempest.wanandroid.interactor.impl.HttpHelperImpl;
import com.ytempest.wanandroid.interactor.impl.PreferencesHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author heqidu
 * @since 2020/6/28
 */
@Module
public class InteractorModule {
    @Provides
    @Singleton
    HttpHelper provideHttpHelper(HttpHelperImpl httpHelperImpl) {
        return httpHelperImpl;
    }

    @Provides
    @Singleton
    DbHelper provideDBHelper(DbHelperImpl realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(PreferencesHelperImpl implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    BaseInteractor provideBaseInteractor(HttpHelper httpHelper, DbHelper dbhelper, PreferencesHelper preferencesHelper) {
        return new BaseInteractor(httpHelper, dbhelper, preferencesHelper);
    }
}
