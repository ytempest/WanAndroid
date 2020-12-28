package com.ytempest.wanandroid.base.fragment.load;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ytempest.wanandroid.R;

import java.util.Map;

/**
 * @author heqidu
 * @since 2020/12/28
 */
public class Loader {

    private final ViewGroup mRootView;
    private final Map<Integer, AbsViewType> mViewTypes = new ArrayMap<>();

    public Loader(ViewGroup rootView) {
        mRootView = rootView;
        mViewTypes.put(ViewType.ERR, new AbsViewType(R.layout.layout_net_error));
        mViewTypes.put(ViewType.LOAD, new AbsViewType(R.layout.layout_main_load));
        final LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        for (AbsViewType viewType : mViewTypes.values()) {
            viewType.setup(mRootView, inflater);
        }
    }

    public ViewGroup getRootView() {
        return mRootView;
    }

    public void setViewListener(@ViewType int type, AbsViewType.OnViewActionListener listener) {
        AbsViewType viewType = mViewTypes.get(type);
        if (viewType != null) {
            viewType.setViewActionListener(listener);
        }
    }

    public void showView(@ViewType int type) {
        for (Map.Entry<Integer, AbsViewType> entry : mViewTypes.entrySet()) {
            AbsViewType viewType = entry.getValue();
            if (entry.getKey() == type) {
                viewType.show();
            } else {
                viewType.hide();
            }
        }
    }

    public void hideView(@ViewType int type) {
        AbsViewType typeView = mViewTypes.get(type);
        if (typeView != null) {
            typeView.hide();
        }
    }

    public void hideAll() {
        for (AbsViewType typeView : mViewTypes.values()) {
            typeView.hide();
        }
    }

}
