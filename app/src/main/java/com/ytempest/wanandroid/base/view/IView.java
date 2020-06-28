package com.ytempest.wanandroid.base.view;

import android.support.annotation.StringRes;

/**
 * @author heqidu
 * @since 2020/6/28
 */
public interface IView {
    void showToast(String msg);

    void showToast(@StringRes int textId);
}
