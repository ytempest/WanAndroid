package com.ytempest.wanandroid.activity.main.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ytempest.banner.Banner;
import com.ytempest.banner.transformers.Transformers;
import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;
import com.ytempest.wanandroid.http.bean.BannerBean;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;

import java.util.List;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/6/23
 */
@InjectLayout(R.layout.frag_home)
public class HomeFrag extends MvpFragment<HomePresenter> implements IHomeContract.View {

    @BindView(R.id.view_banner_view)
    Banner mBannerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBannerView.setBannerBinder(new HomeBannerBinder());
        mBannerView.setPlayDuration(3000);
        mBannerView.setScrollDuration(1500);
        mBannerView.setScrollAnimation(Transformers.DAMPING);
        mPresenter.getBannerList();
    }

    @Override
    public void showBanner(List<BannerBean> data) {
        mBannerView.display(data);
    }

    @Override
    public void displayHomeArticle(HomeArticleBean homeArticleBean) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mBannerView.stopAutoPlay();
        } else {
            mBannerView.startAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBannerView.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerView.stopAutoPlay();
    }
}
