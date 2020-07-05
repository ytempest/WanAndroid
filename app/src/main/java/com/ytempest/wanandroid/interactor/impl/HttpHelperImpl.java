package com.ytempest.wanandroid.interactor.impl;

import com.ytempest.wanandroid.http.HttpApi;
import com.ytempest.wanandroid.http.bean.BaseResp;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.interactor.HttpHelper;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author heqidu
 * @since 2020/6/28
 */
public class HttpHelperImpl implements HttpHelper {

    private final HttpApi mHttpApi;

    @Inject
    public HttpHelperImpl(HttpApi httpApi) {
        mHttpApi = httpApi;
    }

    public Observable<BaseResp<HomeArticleBean>> getHomeArticleList(int pageNum) {
        return mHttpApi.getHomeArticleList(pageNum);
    }
}
