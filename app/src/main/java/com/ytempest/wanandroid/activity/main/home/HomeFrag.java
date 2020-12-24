package com.ytempest.wanandroid.activity.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ytempest.banner.Banner;
import com.ytempest.banner.transformers.Transformers;
import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.tool.util.NetUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.activity.main.home.article.HomeArticleAdapter;
import com.ytempest.wanandroid.base.fragment.MvpFragment;
import com.ytempest.wanandroid.helper.ArticleDetailHelper;
import com.ytempest.wanandroid.http.bean.BannerBean;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.widget.MainLoadView;
import com.ytempest.wanandroid.widget.MaskLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/6/23
 */
@InjectLayout(R.layout.frag_home)
public class HomeFrag extends MvpFragment<HomePresenter> implements IHomeContract.View {

    private static final String TAG = HomeFrag.class.getSimpleName();

    @BindView(R.id.view_main_load_view)
    MainLoadView mLoadView;
    @BindView(R.id.layout_main_mask)
    MaskLayout mErrorLayout;
    @BindView(R.id.view_main_pull_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.view_main_nested_scroll)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.view_main_banner)
    Banner mBannerView;
    @BindView(R.id.rv_main_article_list)
    RecyclerView mRecyclerView;
    private HomeArticleAdapter mAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mErrorLayout.setMaskActionListener(new MaskLayout.SimpleMaskActionListener() {
            @Override
            public void onMaskCreated(View maskView) {
                super.onMaskCreated(maskView);
                maskView.findViewById(R.id.tv_net_error_retry)
                        .setOnClickListener(v -> {
                            if (NetUtils.isNetAvailable(getContext())) {
                                mLoadView.show();
                                mPresenter.loadHomeData();
                            }
                        });
                maskView.findViewById(R.id.tv_net_error_setting_net)
                        .setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_SETTINGS)));
            }
        });
        mRefreshLayout.setColorSchemeResources(R.color.main_color);
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.refreshHomeArticle());

        mBannerView.setBannerBinder(new HomeBannerBinder());
        mBannerView.setPlayDuration(3000);
        mBannerView.setScrollDuration(1500);
        mBannerView.setScrollAnimation(Transformers.DAMPING);

        // 移除RecyclerView更新数据时的动画，防止View闪烁的问题
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeArticleAdapter(mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isArriveBottom()) {
                    mPresenter.loadMoreHomeArticle();
                }
            }
        });
    }

    private boolean isArriveBottom() {
        // RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
        // RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        // 由于NestedScrollView嵌套了RecyclerView，这里需要通过NestedScrollView判断
        boolean isArriveBottom = !mNestedScrollView.canScrollVertically(1);
        boolean isIdleState = mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE;
        return isArriveBottom && isIdleState;
    }

    private void initData() {
        mLoadView.show();
        mPresenter.loadHomeData();
        ArticleDetailHelper.getInstance().getArticleUpdateDetail().observe(getViewLifecycleOwner(), bean -> {
            if (bean == null) return;
            for (HomeArticleBean.DatasBean data : mAdapter.getSrcDataList()) {
                if (data.getId() == bean.getArticleId()) {
                    data.setCollect(bean.isCollected());
                    mAdapter.refresh(data);
                    return;
                }
            }
        });
    }

    @Override
    public void onHomeDataSuccess(List<BannerBean> bannerList, HomeArticleBean bean) {
        mLoadView.hide();
        mErrorLayout.hideMask();
        mBannerView.display(bannerList);
        mAdapter.display(bean.getDatas());
    }

    @Override
    public void onHomeDataFail(int code) {
        mLoadView.hide();
        mErrorLayout.showMask();
    }

    @Override
    public void displayHomeArticle(boolean fromRefresh, HomeArticleBean homeArticleBean) {
        if (fromRefresh) {
            mRefreshLayout.setRefreshing(false);
            mAdapter.display(homeArticleBean.getDatas());
        } else {
            mAdapter.addData(homeArticleBean.getDatas());
        }
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

    @Override
    public void onArticleCollectSuccess(HomeArticleBean.DatasBean data) {
        mAdapter.refresh(data);
    }

    @Override
    public void onArticleCollectFail(HomeArticleBean.DatasBean data, int code) {
        showToast(data.isCollect() ? R.string.collect_fail : R.string.cancel_fail);
    }
}
