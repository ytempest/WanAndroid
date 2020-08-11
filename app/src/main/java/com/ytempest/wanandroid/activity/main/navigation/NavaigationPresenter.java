package com.ytempest.wanandroid.activity.main.navigation;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class NavaigationPresenter extends BasePresenter<INavigationContract.View> implements INavigationContract.Presenter {

    @Inject
    public NavaigationPresenter(BaseInteractor interactor) {
        super(interactor);
    }
}
