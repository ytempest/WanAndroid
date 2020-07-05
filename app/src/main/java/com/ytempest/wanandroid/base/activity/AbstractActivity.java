package com.ytempest.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ytempest.layoutinjector.LayoutInjector;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author heqidu
 * @since 2020/6/20
 */
public abstract class AbstractActivity extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInjector.inject(this);
        mBind = ButterKnife.bind(this);
        onViewCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
    }

    protected abstract void onViewCreated();
}
