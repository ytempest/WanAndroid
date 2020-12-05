package com.ytempest.wanandroid.base;

import android.app.Dialog;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ytempest.layoutinjector.LayoutInjector;
import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.R;

/**
 * @author heqidu
 * @since 2020/8/13
 */
public class BaseDialog extends Dialog {

    private static final String TAG = BaseDialog.class.getSimpleName();

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.common_dialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        LayoutInjector.inject(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismiss();
    }

    protected void setBlockBackKey(boolean block) {
        this.setOnKeyListener((dialog, keyCode, event) -> block);
    }

    public void setAutoDismiss(Lifecycle lifecycle) {
        lifecycle.addObserver((GenericLifecycleObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        try {
            if (!isShowing()) {
                super.show();
            }
        } catch (Throwable e) {
            LogUtils.d(TAG, "show: e=" + e);
        }
    }

    @Override
    public void dismiss() {
        try {
            if (isShowing()) {
                super.dismiss();
            }
        } catch (Throwable e) {
            LogUtils.d(TAG, "dismiss: e=" + e);
        }
    }
}
