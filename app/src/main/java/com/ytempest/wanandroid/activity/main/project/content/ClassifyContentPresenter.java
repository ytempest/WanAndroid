package com.ytempest.wanandroid.activity.main.project.content;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public class ClassifyContentPresenter extends BasePresenter<IClassifyContentContract.View> implements IClassifyContentContract.Presenter {

    @Inject
    public ClassifyContentPresenter(BaseInteractor interactor) {
        super(interactor);
    }
}
