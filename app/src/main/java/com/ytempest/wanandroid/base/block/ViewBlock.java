package com.ytempest.wanandroid.base.block;

import butterknife.ButterKnife;

/**
 * @author heqidu
 * @since 2020/6/21
 */
public abstract class ViewBlock extends Block {

    @Override
    protected void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this, getHost());
    }
}
