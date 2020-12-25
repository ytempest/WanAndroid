package com.ytempest.wanandroid.activity.main.home;

import android.support.annotation.NonNull;

import com.ytempest.tool.util.DataUtils;
import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.utils.PageCtrl;
import com.ytempest.wanandroid.utils.PageCtrl.State;
import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.ArticleCollectBean;
import com.ytempest.wanandroid.http.bean.BannerBean;
import com.ytempest.wanandroid.http.bean.BaseResp;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.http.observer.HandlerObserver;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class HomePresenter extends BasePresenter<IHomeContract.View> implements IHomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();
    private final PageCtrl mPageState = new PageCtrl();

    @Inject
    public HomePresenter(BaseInteractor interactor) {
        super(interactor);
    }

    @Override
    public boolean isUserLogin() {
        return mInteractor.getConfigs().getUser().isUserLogin();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadHomeData() {
        if (mPageState.isRequesting()) return;
        mPageState.moveTo(State.REFRESH);
        Observable.merge(
                mInteractor.getHttpHelper().getBannerList(),
                mInteractor.getHttpHelper().getHomeArticleList(mPageState.getNextPage())
        ).compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver(mView, false) {
                    private List<BannerBean> mBannerList;
                    private HomeArticleBean mArticleBean;

                    @Override
                    protected void onSuccess(@NonNull Object data) {
                        if (data instanceof List) {
                            mBannerList = (List<BannerBean>) data;
                        } else if (data instanceof HomeArticleBean) {
                            mArticleBean = (HomeArticleBean) data;
                        }

                        if (!DataUtils.isNull(mBannerList, mArticleBean)) {
                            mPageState.moveTo(State.SUCCESS);
                            mView.onHomeDataSuccess(mBannerList, mArticleBean);
                        }
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        mPageState.moveTo(State.FAIL);
                        mView.onHomeDataFail(code);
                    }
                });
    }

    @Override
    public void refreshHomeArticle() {
        if (mPageState.isRequesting()) return;
        mPageState.moveTo(State.REFRESH);
        mInteractor.getHttpHelper().getHomeArticleList(mPageState.getNextPage())
                .compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver<HomeArticleBean>(mView) {
                    @Override
                    protected void onSuccess(@NonNull HomeArticleBean homeArticleBean) {
                        mView.displayHomeArticle(true, homeArticleBean);
                        mPageState.moveTo(PageCtrl.State.SUCCESS);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        super.onFail(code, e);
                        mPageState.moveTo(PageCtrl.State.FAIL);
                    }
                });
    }

    @Override
    public void loadMoreHomeArticle() {
        if (mPageState.isRequesting()) return;
        mPageState.moveTo(PageCtrl.State.LOAD_MORE);
        final int version = mPageState.getVersion();
        mInteractor.getHttpHelper().getHomeArticleList(mPageState.getNextPage())
                .compose(RxUtils.switchScheduler())
                .filter(resp -> {
                    LogUtils.d(TAG, "requestHomeArticle: 本次请求结果是否可用: " + mPageState.isSameVersion(version));
                    return mPageState.isSameVersion(version);
                })
                .subscribe(new HandlerObserver<HomeArticleBean>(mView) {
                    @Override
                    protected void onSuccess(@NonNull HomeArticleBean homeArticleBean) {
                        mView.displayHomeArticle(false, homeArticleBean);
                        mPageState.moveTo(PageCtrl.State.SUCCESS);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        super.onFail(code, e);
                        mPageState.moveTo(PageCtrl.State.FAIL);
                    }
                });
    }

    @Override
    public void updateArticleCollectStatus(HomeArticleBean.DatasBean data) {
        Observable<BaseResp<ArticleCollectBean>> collectObservable;
        boolean isCollect = !data.isCollect();
        if (isCollect) { // 收藏文章
            collectObservable = mInteractor.getHttpHelper().addCollectArticle(data.getId());
        } else { // 取消收藏
            collectObservable = mInteractor.getHttpHelper().cancelCollectArticle(data.getId());
        }

        mView.showLoading();
        collectObservable.compose(RxUtils.switchScheduler())
                .map(RxUtils.checkArticleCollectData())
                .subscribe(new HandlerObserver<ArticleCollectBean>(mView, false) {
                    @Override
                    protected void onSuccess(@NonNull ArticleCollectBean bean) {
                        mView.stopLoading();
                        data.setCollect(isCollect);
                        mView.onArticleCollectSuccess(data);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        mView.stopLoading();
                        data.setCollect(isCollect);
                        mView.onArticleCollectFail(data, code);
                    }
                });
    }

}
