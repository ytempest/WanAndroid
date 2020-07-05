package com.ytempest.wanandroid.http.observer;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.view.IView;

/**
 * @author heqidu
 * @since 2020/7/4
 */
public abstract class HandlerObserver<T> extends BaseObserver<T> {

    private IView mView;
    @StringRes
    private Integer mErrMsgId;
    private String mErrMsg;
    private boolean showErrMsg;

    public HandlerObserver(IView view) {
        this(view, true);
    }

    public HandlerObserver(IView view, boolean showErrMsg) {
        this.mView = view;
        this.showErrMsg = showErrMsg;
    }

    public HandlerObserver(IView view, @StringRes int errMsgId) {
        mView = view;
        mErrMsgId = errMsgId;
    }

    public HandlerObserver(IView view, String errMsg) {
        mView = view;
        mErrMsg = errMsg;
    }

    @Override
    protected void onFail(@Error int reason, Throwable e) {
        if (mView == null) return;

        if (mErrMsgId != null) {
            mView.showToast(mErrMsgId);
            return;
        }

        if (!TextUtils.isEmpty(mErrMsg)) {
            mView.showToast(mErrMsg);
            return;
        }

        if (showErrMsg) {
            switch (reason) {
                case Error.NET_ERR:
                    mView.showToast(R.string.net_err);
                    break;
                case Error.DATA_ERR:
                    mView.showToast(R.string.get_data_fail);
                    break;
                case Error.REQUEST_ERR:
                    mView.showToast(R.string.request_fail);
                    break;
                case Error.UNKNOWN_ERR:
                    mView.showToast(R.string.unknown_err);
                    break;
            }
        }
    }
}
