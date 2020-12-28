package com.ytempest.wanandroid.base.fragment.load;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.ytempest.tool.util.NetUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;
import com.ytempest.wanandroid.base.presenter.IPresenter;

/**
 * @author heqidu
 * @since 2020/12/28
 */
public class LoaderFrag<Presenter extends IPresenter> extends MvpFragment<Presenter> {

    private Loader mLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoader = new Loader((ViewGroup) view);
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
