package com.ytempest.wanandroid.base.unit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author heqidu
 * @since 2020/6/24
 */
public class ViewUnit extends Unit {
    @Override
    public void onCreateView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreateView(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
