package com.ytempest.wanandroid.activity.login;

import com.ytempest.wanandroid.base.presenter.BasePresenter;
import com.ytempest.wanandroid.http.bean.LoginBean;
import com.ytempest.wanandroid.http.observer.OptionalObserver;
import com.ytempest.wanandroid.interactor.impl.BaseInteractor;
import com.ytempest.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * @author heqidu
 * @since 2020/8/11
 */
public class LoginPresenter extends BasePresenter<ILoginContract.View> implements ILoginContract.Presenter {

    @Inject
    public LoginPresenter(BaseInteractor interactor) {
        super(interactor);
    }

    @Override
    public void login(String account, String password) {
        mInteractor.getHttpHelper().login(account, password)
                .compose(RxUtils.switchScheduler())
                .subscribe(new OptionalObserver<LoginBean>()
                        .doOnStart(aVoid -> mView.showLoading())
                        .doOnSuccess(loginBean -> mView.onLoginSuccess(loginBean))
                        .doOnFail((code, throwable) -> mView.onLoginFail(code, throwable))
                        .doOnCompleted(aVoid -> mView.stopLoading())
                );
    }
}
