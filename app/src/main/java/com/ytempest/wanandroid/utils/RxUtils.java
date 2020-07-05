package com.ytempest.wanandroid.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author heqidu
 * @since 2020/7/4
 */
public class RxUtils {
    /**
     * 切换至子线程处理，并在主线程观察
     */
    public static <T> ObservableTransformer<T, T> switchScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
