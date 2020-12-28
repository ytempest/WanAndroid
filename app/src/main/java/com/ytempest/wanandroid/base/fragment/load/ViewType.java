package com.ytempest.wanandroid.base.fragment.load;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author heqidu
 * @since 2020/12/28
 */
@IntDef({ViewType.ERR, ViewType.LOAD,})
@Retention(RetentionPolicy.SOURCE)
public @interface ViewType {
    int ERR = 1;
    int LOAD = 2;
}
