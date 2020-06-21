package com.ytempest.wanandroid.base.block;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * @author heqidu
 * @since 2020/6/21
 */
public abstract class ViewBlock extends Block {

    protected void setContentView(@LayoutRes int layoutResID) {
        getHost().setContentView(layoutResID);
    }

    protected <V extends View> V findViewById(@IdRes int id) {
        return getHost().findViewById(id);
    }
}
