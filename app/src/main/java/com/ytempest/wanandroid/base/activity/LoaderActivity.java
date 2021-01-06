package com.ytempest.wanandroid.base.activity;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;

import com.ytempest.tool.util.NetUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.load.AbsViewType;
import com.ytempest.wanandroid.base.load.Loader;
import com.ytempest.wanandroid.base.load.ViewType;
import com.ytempest.wanandroid.base.presenter.IPresenter;

/**
 * @author ytempest
 * @since 2021/1/6
 */
public abstract class LoaderActivity<Presenter extends IPresenter> extends MvpActivity<Presenter> {

    private Loader mLoader;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        ViewGroup root = findViewById(android.R.id.content);
        mLoader = new Loader(root);
        mLoader.setViewListener(ViewType.ERR, new AbsViewType.OnViewActionListener() {
            @Override
            public void onViewCreated(View view) {
                super.onViewCreated(view);
                view.findViewById(R.id.tv_net_error_retry)
                        .setOnClickListener(v -> {
                            if (NetUtils.isNetAvailable(getContext())) {
                                mLoader.showView(ViewType.LOAD);
                                onReloadClick();
                            }
                        });
                view.findViewById(R.id.tv_net_error_setting_net)
                        .setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_SETTINGS)));
            }
        });
        mLoader.setViewListener(ViewType.LOAD, new AbsViewType.OnViewActionListener());
    }

    protected void onReloadClick() {

    }

    public Loader getLoader() {
        return mLoader;
    }
}
