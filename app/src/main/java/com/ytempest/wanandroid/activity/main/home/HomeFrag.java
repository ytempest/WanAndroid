package com.ytempest.wanandroid.activity.main.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.BaseFragment;

/**
 * @author heqidu
 * @since 2020/6/23
 */
public class HomeFrag extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_home, container, false);
    }
}
