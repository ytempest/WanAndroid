package com.ytempest.wanandroid.activity.main.project.content;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public class ProjectContentPresenter extends BasePresenter<IProjectContentContract.View> implements IProjectContentContract.Presenter {

    @Inject
    public ProjectContentPresenter(BaseInteractor interactor) {
        super(interactor);
    }
}
