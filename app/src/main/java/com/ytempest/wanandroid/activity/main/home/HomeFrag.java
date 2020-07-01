package com.ytempest.wanandroid.activity.main.home;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;

/**
 * @author heqidu
 * @since 2020/6/23
 */
public class HomeFrag extends MvpFragment<HomePresenter> implements IHomeContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }
}
