package com.ytempest.wanandroid.http.observer;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.ytempest.wanandroid.http.bean.BaseResp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DefaultObserver;
import retrofit2.HttpException;

/**
 * @author heqidu
 * @since 2020/7/4
 */
public abstract class BaseObserver<T> extends DefaultObserver<BaseResp<T>> {

    @Override
    public void onNext(BaseResp<T> resp) {
        if (resp == null || !resp.isSuccess() || resp.getData() == null) {
            onFail(Error.DATA_ERR, new DataErrException());
            return;
        }
        onSuccess(resp.getData());
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            onFail(Error.NET_ERR, e);

        } else if (e instanceof SocketTimeoutException) {
            onFail(Error.REQUEST_ERR, e);

        } else {
            onFail(Error.UNKNOWN_ERR, e);
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(@NonNull T t);

    protected abstract void onFail(@Error int reason, Throwable e);

    @IntDef({Error.NET_ERR, Error.DATA_ERR,
            Error.REQUEST_ERR, Error.UNKNOWN_ERR,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
        int NET_ERR = 1;
        int DATA_ERR = 2;
        int REQUEST_ERR = 3;
        int UNKNOWN_ERR = 4;
    }
}
