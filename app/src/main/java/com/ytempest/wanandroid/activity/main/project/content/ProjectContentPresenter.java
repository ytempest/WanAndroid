package com.ytempest.wanandroid.activity.main.project.content;

import android.support.annotation.NonNull;

import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.MyCollectionBean;
import com.ytempest.wanandroid.http.bean.ProjectClassifyBean;
import com.ytempest.wanandroid.http.bean.ProjectContentBean;
import com.ytempest.wanandroid.http.observer.HandlerObserver;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.utils.JSON;
import com.ytempest.wanandroid.utils.PageCtrl;
import com.ytempest.wanandroid.utils.PageCtrl.State;
import com.ytempest.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public class ProjectContentPresenter extends BasePresenter<IProjectContentContract.View> implements IProjectContentContract.Presenter {

    private static final String TAG = ProjectContentPresenter.class.getSimpleName();
    private final PageCtrl mPageCtrl = new PageCtrl();

    @Inject
    public ProjectContentPresenter(BaseInteractor interactor) {
        super(interactor);
        mInteractor.getHttpHelper().getMyCollectionList()
                .compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver<MyCollectionBean>(mView) {
                    @Override
                    protected void onSuccess(@NonNull MyCollectionBean myCollectionBean) {
                        myCollectionBean.setDatas(null);
                        LogUtils.d(TAG, "onSuccess: myCollectionBean=" + JSON.toJson(myCollectionBean));
                    }
                });
    }

    @Override
    public void refreshContent(ProjectClassifyBean classify) {
        mPageCtrl.moveTo(State.REFRESH);
        mInteractor.getHttpHelper().getProjectContent(mPageCtrl.getNextPage(), classify.getId())
                .compose(RxUtils.switchScheduler())
                .filter(mPageCtrl.filterDirtyData())
                .subscribe(new HandlerObserver<ProjectContentBean>(mView) {
                    @Override
                    protected void onSuccess(@NonNull ProjectContentBean projectContent) {
                        mPageCtrl.moveTo(State.SUCCESS);
                        mView.displayProjectContent(projectContent);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        mView.onProjectContentFail(code);
                        mPageCtrl.moveTo(State.FAIL);
                    }
                });
    }

    @Override
    public void loadMoreProjectContent(ProjectClassifyBean classifyBean) {
        if (mPageCtrl.isRequesting()) return;
        mPageCtrl.moveTo(State.LOAD_MORE);
        mInteractor.getHttpHelper().getProjectContent(mPageCtrl.getNextPage(), classifyBean.getId())
                .compose(RxUtils.switchScheduler())
                .filter(mPageCtrl.filterDirtyData())
                .subscribe(new HandlerObserver<ProjectContentBean>(mView) {
                    @Override
                    protected void onSuccess(@NonNull ProjectContentBean projectContent) {
                        mPageCtrl.moveTo(State.SUCCESS);
                        mView.onMoreProjectContentLoaded(projectContent);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        super.onFail(code, e);
                        mPageCtrl.moveTo(State.FAIL);
                    }
                });
    }

    @Override
    public void addProjectArticleCollect(ProjectContentBean.DatasBean bean) {
        mView.showLoading();
        mInteractor.getHttpHelper().addCollectOutsideArticle(bean.getTitle(), bean.getAuthor(), bean.getLink())
                .compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(@NonNull Object data) {
                        mView.stopLoading();
                        bean.setCollect(true);
                        mView.onProjectArticleCollectSuccess(bean);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        mView.stopLoading();
                        // 在这个接口服务器返回-1表示已经收藏过了
                        boolean onceCollected = code == -1;
                        mView.onProjectArticleCollectFail(code, onceCollected, bean);
                    }
                });
    }

}
