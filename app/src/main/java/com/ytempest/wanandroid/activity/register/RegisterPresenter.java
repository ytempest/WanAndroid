package com.ytempest.wanandroid.activity.register;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.LoginBean;
import com.ytempest.wanandroid.http.observer.OptionalObserver;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/8/14
 */
public class RegisterPresenter extends BasePresenter<IRegisterContract.View> implements IRegisterContract.Presenter {

    @Inject
    public RegisterPresenter(BaseInteractor interactor) {
        super(interactor);
    }

    @Override
    public void register(String account, String pwd, String confirmPwd) {
        mInteractor.getHttpHelper().register(account, pwd, confirmPwd)
                .compose(RxUtils.switchScheduler())
                .subscribe(new OptionalObserver<LoginBean>()
                        .doOnStart(aVoid -> mView.showLoading())
                        .doOnCompleted(aVoid -> mView.stopLoading())
                        .doOnSuccess(loginBean -> mView.onRegisterSuccess(loginBean))
                        .doOnFail((code, throwable) -> mView.onRegisterFail(code, throwable))
                );
    }
}
