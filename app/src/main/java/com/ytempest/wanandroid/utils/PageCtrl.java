package com.ytempest.wanandroid.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heqidu
 * @since 2020/12/25
 * 文章页面页码状态控制器
 */
public final class PageCtrl {
    private final AtomicInteger version = new AtomicInteger();
    private int nextPage = 0;
    private boolean isRequesting;

    public int getVersion() {
        return version.get();
    }

    public boolean isSameVersion(int version) {
        return this.version.get() == version;
    }

    public int getNextPage() {
        return nextPage;
    }

    public boolean isRequesting() {
        return isRequesting;
    }

    public void moveTo(@State int state) {
        switch (state) {
            case State.REFRESH:
                isRequesting = true;
                nextPage = 0;
                version.incrementAndGet();
                break;

            case State.LOAD_MORE:
                isRequesting = true;
                break;

            case State.SUCCESS:
                isRequesting = false;
                nextPage++;
                break;

            case State.FAIL:
                isRequesting = false;
                break;

            default:
                break;
        }
    }

    @IntDef({State.REFRESH, State.LOAD_MORE, State.SUCCESS, State.FAIL,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int REFRESH = 1;
        int LOAD_MORE = 2;
        int SUCCESS = 3;
        int FAIL = 4;
    }
}
