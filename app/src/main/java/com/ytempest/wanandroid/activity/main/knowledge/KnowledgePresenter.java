package com.ytempest.wanandroid.activity.main.knowledge;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public class KnowledgePresenter extends BasePresenter<IKnowledgeContract.View> implements IKnowledgeContract.Presenter {

    @Inject
    public KnowledgePresenter(BaseInteractor interactor) {
        super(interactor);
    }
}
