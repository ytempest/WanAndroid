package com.ytempest.wanandroid.interactor;

/**
 * @author heqidu
 * @since 2020/6/28
 */
public interface MvpInteractor {

    public HttpHelper getHttpHelper();

    public DbHelper getDbHelper();

    public PreferencesHelper getPreferencesHelper();

}
