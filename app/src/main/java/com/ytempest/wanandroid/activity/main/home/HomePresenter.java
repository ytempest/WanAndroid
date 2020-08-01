package com.ytempest.wanandroid.activity.main.home;

import android.support.annotation.NonNull;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.http.observer.HandlerObserver;
import com.ytempest.wanandroid.utils.RxUtils;

import dagger.Module;
import dagger.Provides;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class HomePresenter extends BasePresenter<IHomeContract.View> implements IHomeContract.Presenter {

    private int mCurPage;

    @Override
    public void loadHomeArticle() {
        mInteractor.getHttpHelper().getHomeArticleList(mCurPage)
                .compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver<HomeArticleBean>(mView) {
                    @Override
                    protected void onSuccess(@NonNull HomeArticleBean homeArticleBean) {
                        mView.displayHomeArticle(homeArticleBean);
                    }
                });
    }
}
