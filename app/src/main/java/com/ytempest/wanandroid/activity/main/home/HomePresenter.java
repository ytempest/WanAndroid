package com.ytempest.wanandroid.activity.main.home;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class HomePresenter extends BasePresenter<IHomeContract.View> implements IHomeContract.Presenter {

    @Inject
    public HomePresenter(BaseInteractor interactor) {
        super(interactor);
    }
}
