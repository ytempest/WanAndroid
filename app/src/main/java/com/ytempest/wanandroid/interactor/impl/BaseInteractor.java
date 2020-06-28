package com.ytempest.wanandroid.interactor.impl;

import com.ytempest.wanandroid.interactor.DbHelper;
import com.ytempest.wanandroid.interactor.HttpHelper;
import com.ytempest.wanandroid.interactor.MvpInteractor;
import com.ytempest.wanandroid.interactor.PreferencesHelper;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/28
 */
public class BaseInteractor implements MvpInteractor {
    private final HttpHelper mHttpHelper;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public BaseInteractor(HttpHelper httpHelper, DbHelper dbHelper, PreferencesHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public HttpHelper getHttpHelper() {
        return mHttpHelper;
    }

    @Override
    public DbHelper getDbHelper() {
        return mDbHelper;
    }

    @Override
    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }
}
