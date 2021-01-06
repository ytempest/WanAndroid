package com.ytempest.wanandroid.base.load;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author heqidu
 * @since 2020/12/28
 */
public class AbsViewType {

    private final int layoutId;
    private LayoutInflater mInflater;
    private ViewGroup mRootView;
    private View view;

    public AbsViewType(int layoutId) {
        this.layoutId = layoutId;
    }

    void setup(ViewGroup rootView, LayoutInflater inflater) {
        mRootView = rootView;
        mInflater = inflater;
    }

    public void show() {
        initView();
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            mListener.onViewShow(view);
        }
    }

    public void hide() {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            mListener.onViewHide(view);
        }
    }

    private void initView() {
        if (view == null) {
            view = mInflater.inflate(layoutId, mRootView, false);
            mListener.onViewCreated(view);
            // 覆盖底层View的点击和滚动
            view.setClickable(true);

            mRootView.addView(view);
            view.setVisibility(View.GONE);
        }
    }

    private OnViewActionListener mListener = new OnViewActionListener();

    public void setViewActionListener(OnViewActionListener listener) {
        if (listener != null) {
            this.mListener = listener;
        }
    }

    public static class OnViewActionListener {
        public void onViewCreated(View view) {
        }

        public void onViewShow(View view) {
        }

        public void onViewHide(View view) {
        }
    }
}
