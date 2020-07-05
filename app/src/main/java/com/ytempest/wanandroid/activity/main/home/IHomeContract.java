package com.ytempest.wanandroid.activity.main.home;

import com.ytempest.wanandroid.base.presenter.IPresenter;
import com.ytempest.wanandroid.base.view.IView;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public interface IHomeContract {
    interface View extends IView {
        void displayHomeArticle(HomeArticleBean homeArticleBean);
    }

    interface Presenter extends IPresenter {
        void loadHomeArticle();
    }
}
