package com.ytempest.wanandroid.activity.main.navigation;

import android.support.annotation.NonNull;

import com.ytempest.tool.util.DataUtils;
import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.NavigationListBean;
import com.ytempest.wanandroid.http.observer.HandlerObserver;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.utils.JSON;
import com.ytempest.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class NavaigationPresenter extends BasePresenter<INavigationContract.View> implements INavigationContract.Presenter {

    private static final String TAG = NavaigationPresenter.class.getSimpleName();

    @Inject
    public NavaigationPresenter(BaseInteractor interactor) {
        super(interactor);
    }

    @Override
    public void getNavigationList() {
        mInteractor.getHttpHelper().getNavigationList()
                .compose(RxUtils.switchScheduler())
                .subscribe(new HandlerObserver<List<NavigationListBean>>(mView) {
                    @Override
                    protected void onSuccess(@NonNull List<NavigationListBean> list) {
                        mView.displayNavigationList(list);
                    }

                    @Override
                    protected void onFail(int code, Throwable e) {
                        mView.onNavigationListFail(code);
                    }
                });
    }
}
