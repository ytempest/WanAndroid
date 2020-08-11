package com.ytempest.wanandroid.activity.main.home;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;

/**
 * @author heqidu
 * @since 2020/6/23
 */
@InjectLayout(R.layout.frag_home)
public class HomeFrag extends MvpFragment<HomePresenter> implements IHomeContract.View {

    @Override
    public void displayHomeArticle(HomeArticleBean homeArticleBean) {

    }
}
