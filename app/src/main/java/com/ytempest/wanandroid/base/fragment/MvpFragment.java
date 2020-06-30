package com.ytempest.wanandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ytempest.wanandroid.base.presenter.IPresenter;
import com.ytempest.wanandroid.base.view.IView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * @author heqidu
 * @since 2020/6/30
 */
public abstract class MvpFragment<Presenter extends IPresenter> extends AbstractFragment implements IView {

    @Inject
    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroyView();
    }


    /*View*/

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showToast(int textId) {

    }
}
