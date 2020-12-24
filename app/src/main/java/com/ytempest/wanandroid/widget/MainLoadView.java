package com.ytempest.wanandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ytempest.wanandroid.R;

/**
 * @author heqidu
 * @since 2020/12/24
 * TODO: 添加动画
 */
public class MainLoadView extends FrameLayout {

    public MainLoadView(Context context) {
        this(context, null);
    }

    public MainLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_main_load, this, true);
        setClickable(true);
        setBackgroundResource(R.color.common_bg);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }
}
