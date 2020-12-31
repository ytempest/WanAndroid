package com.ytempest.wanandroid.activity.main.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.load.LoaderFrag;
import com.ytempest.wanandroid.base.fragment.load.ViewType;
import com.ytempest.wanandroid.http.bean.NavigationListBean;

import java.util.List;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/6/23
 */
@InjectLayout(R.layout.frag_navigation)
public class NavigationFrag extends LoaderFrag<NavaigationPresenter> implements INavigationContract.View {

    private static final String TAG = NavigationFrag.class.getSimpleName();

    @BindView(R.id.group_navigation_title_list)
    RecyclerView mTitleListView;
    @BindView(R.id.group_navigation_content_list)
    RecyclerView mContentListView;
    private NavigationTitleAdapter mTitleAdapter;
    private NavigationContentAdapter mContentAdapter;
    private LinearLayoutManager mContentManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleListView.setHasFixedSize(true);
        mTitleListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTitleAdapter = new NavigationTitleAdapter();
        mTitleListView.setAdapter(mTitleAdapter);

        mContentListView.setHasFixedSize(true);
        mContentListView.setLayoutManager(mContentManager = new LinearLayoutManager(getContext()));
        mContentListView.setAdapter(mContentAdapter = new NavigationContentAdapter());
        mContentListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !mContentAdapter.isEmpty()) {
                    int firstVisiblePosition = mContentManager.findFirstVisibleItemPosition();
                    mTitleAdapter.setSelectPosition(firstVisiblePosition);
                }
            }
        });
        loadData();
    }

    private void loadData() {
        getLoader().showView(ViewType.LOAD);
        mPresenter.getNavigationList();
    }

    @Override
    public void displayNavigationList(List<NavigationListBean> list) {
        getLoader().hideAll();
        mTitleAdapter.display(list);
        mContentAdapter.display(list);
    }

    @Override
    public void onNavigationListFail(int code) {
        getLoader().showView(ViewType.ERR);
    }

    @Override
    protected void onReloadClick() {
        super.onReloadClick();
        loadData();
    }
}
