package com.ytempest.wanandroid.download;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author heqidu
 * @since 2020/10/10
 */
@StringDef({
        NotiClickAction.ON_PREPARE_CLICK,
        NotiClickAction.ON_SUCCESS_CLICK,
        NotiClickAction.ON_FAIL_CLICK,
        NotiClickAction.ON_CONTINUE_CLICK,
        NotiClickAction.ON_PAUSE_CLICK,
})
@Retention(RetentionPolicy.SOURCE)
public @interface NotiClickAction {
    String ON_PREPARE_CLICK = "action_on_prepare_click";
    String ON_SUCCESS_CLICK = "action_on_success_click";
    String ON_FAIL_CLICK = "action_on_fail_click";
    String ON_CONTINUE_CLICK = "action_on_continue_click";
    String ON_PAUSE_CLICK = "action_on_pause_click";
}
