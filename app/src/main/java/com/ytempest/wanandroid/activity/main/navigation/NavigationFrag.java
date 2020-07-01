package com.ytempest.wanandroid.activity.main.navigation;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;

/**
 * @author heqidu
 * @since 2020/6/23
 */
public class NavigationFrag extends MvpFragment<NavaigationPresenter> implements INavigationContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_navigation;
    }

}
