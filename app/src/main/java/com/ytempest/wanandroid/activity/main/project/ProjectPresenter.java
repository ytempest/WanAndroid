package com.ytempest.wanandroid.activity.main.project;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class ProjectPresenter extends BasePresenter<IProjectContract.View> implements IProjectContract.Presenter {

    @Inject
    public ProjectPresenter(BaseInteractor interactor) {
        super(interactor);
    }

}
