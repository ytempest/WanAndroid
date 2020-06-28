package com.ytempest.wanandroid.interactor.impl;

import com.ytempest.wanandroid.interactor.HttpHelper;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/28
 */
public class HttpHelperImpl implements HttpHelper {
    @Inject
    public HttpHelperImpl() {
    }

    @Override
    public String getData() {
        return "1111111111111111";
    }
}
