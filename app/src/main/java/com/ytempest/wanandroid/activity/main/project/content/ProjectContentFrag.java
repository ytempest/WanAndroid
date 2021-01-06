package com.ytempest.wanandroid.activity.main.project.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.activity.main.project.content.list.ContentListAdapter;
import com.ytempest.wanandroid.base.fragment.LoaderFrag;
import com.ytempest.wanandroid.base.load.ViewType;
import com.ytempest.wanandroid.http.bean.ProjectClassifyBean;
import com.ytempest.wanandroid.http.bean.ProjectContentBean;
import com.ytempest.wanandroid.utils.JSON;
import com.ytempest.wanandroid.utils.Utils;

import java.util.Objects;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/12/24
 */
@InjectLayout(R.layout.frag_project_content)
public class ProjectContentFrag extends LoaderFrag<ProjectContentPresenter> implements IProjectContentContract.View {

    private static final String KEY_CLASSIFY_DATA = "classify_data";

    public static ProjectContentFrag newInstance(ProjectClassifyBean data) {
        ProjectContentFrag frag = new ProjectContentFrag();
        Bundle bundle = frag.getBundle();
        bundle.putString(KEY_CLASSIFY_DATA, JSON.toJson(data));
        return frag;
    }

    private Bundle getBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            setArguments(bundle);
        }
        return bundle;
    }

    @BindView(R.id.group_project_content_list)
    RecyclerView mRecyclerView;
    private ProjectClassifyBean mClassifyBean;
    private ContentListAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassifyBean = JSON.from(getBundle().getString(KEY_CLASSIFY_DATA), ProjectClassifyBean.class);
        Objects.requireNonNull(mClassifyBean);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContentListAdapter(mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && Utils.isArriveBottom(recyclerView)) {
                    mPresenter.loadMoreProjectContent(mClassifyBean);
                }
            }
        });
        getLoader().showView(ViewType.LOAD);
        mPresenter.refreshContent(mClassifyBean);
    }


    @Override
    protected void onReloadClick() {
        super.onReloadClick();
        mPresenter.refreshContent(mClassifyBean);
    }

    @Override
    public void displayProjectContent(ProjectContentBean projectContent) {
        getLoader().hideAll();
        if (projectContent.isOver()) {
            showToast(R.string.arrived_end);
        } else {
            mAdapter.display(projectContent.getDatas());
        }
    }

    @Override
    public void onProjectContentFail(int code) {
        if (mAdapter.isEmpty()) {
            getLoader().showView(ViewType.ERR);
        }
    }

    @Override
    public void onMoreProjectContentLoaded(ProjectContentBean projectContent) {
        if (projectContent.isOver()) {
            showToast(R.string.arrived_end);
        } else {
            mAdapter.addData(projectContent.getDatas());
        }
    }

    @Override
    public void onProjectArticleCollectSuccess(ProjectContentBean.DatasBean bean) {
        showToast(R.string.collect_success);
    }

    @Override
    public void onProjectArticleCollectFail(int code, boolean onceCollected, ProjectContentBean.DatasBean bean) {
        showToast(onceCollected ? R.string.once_collected : R.string.collect_fail);
    }
}
