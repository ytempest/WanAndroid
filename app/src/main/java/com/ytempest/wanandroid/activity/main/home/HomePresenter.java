package com.ytempest.wanandroid.activity.main.home;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.activity.main.home.HomePresenter.PageCtrl.State;
import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.ArticleCollectBean;
import com.ytempest.wanandroid.http.bean.BannerBean;
import com.ytempest.wanandroid.http.bean.BaseResp;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.http.observer.HandlerObserver;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.utils.RxUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void getBannerList() {
        mInteractor.getHttpHelper().getBannerList()
                .compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver<List<BannerBean>>(mView) {
                    @Override
                    protected void onSuccess(@NonNull List<BannerBean> data) {
                        mView.showBanner(data);
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
                .subscribe(new HandlerObserver<ArticleCollectBean>(mView) {
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

    /**
     * 文章页面页码状态控制器
     */
    static final class PageCtrl {
        private final AtomicInteger version = new AtomicInteger();
        private int nextPage = 0;
        private boolean isRequesting;

        int getVersion() {
            return version.get();
        }

        boolean isSameVersion(int version) {
            return this.version.get() == version;
        }

        int getNextPage() {
            return nextPage;
        }

        boolean isRequesting() {
            return isRequesting;
        }

        void moveTo(@State int state) {
            switch (state) {
                case State.REFRESH:
                    isRequesting = true;
                    nextPage = 0;
                    version.incrementAndGet();
                    break;

                case State.LOAD_MORE:
                    isRequesting = true;
                    break;

                case State.SUCCESS:
                    isRequesting = false;
                    nextPage++;
                    break;

                case State.FAIL:
                    isRequesting = false;
                    break;

                default:
                    break;
            }
        }

        @IntDef({State.REFRESH, State.LOAD_MORE, State.SUCCESS, State.FAIL,})
        @Retention(RetentionPolicy.SOURCE)
        @interface State {
            int REFRESH = 1;
            int LOAD_MORE = 2;
            int SUCCESS = 3;
            int FAIL = 4;
        }
    }
}
