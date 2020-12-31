package com.ytempest.wanandroid.activity.main.navigation;

import com.ytempest.wanandroid.base.presenter.IPresenter;
import com.ytempest.wanandroid.base.view.IView;
import com.ytempest.wanandroid.http.bean.NavigationListBean;

import java.util.List;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public interface INavigationContract {
    interface View extends IView {

        void displayNavigationList(List<NavigationListBean> list);

        void onNavigationListFail(int code);
    }

    interface Presenter extends IPresenter {
        void getNavigationList();
    }
}
