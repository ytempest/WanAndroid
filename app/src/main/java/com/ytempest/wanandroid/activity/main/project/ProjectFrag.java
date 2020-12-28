package com.ytempest.wanandroid.activity.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.tool.util.DataUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;
import com.ytempest.wanandroid.http.bean.ProjectClassifyBean;

import java.util.List;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/6/23
 */
@InjectLayout(R.layout.frag_project)
public class ProjectFrag extends MvpFragment<ProjectPresenter> implements IProjectContract.View {

    private static final String TAG = ProjectFrag.class.getSimpleName();

    @BindView(R.id.group_project_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_project_content)
    ViewPager mViewPager;
    private ProjectClassifyAdapter mAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter = new ProjectClassifyAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedTab(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setSelectedTab(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mPresenter.getProjectClassify();
    }

    @Override
    public void onProjectClassifyReceived(List<ProjectClassifyBean> list) {
        mAdapter.display(list);
        updateTabView(list);
    }

    private void updateTabView(List<ProjectClassifyBean> list) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0, count = DataUtils.getSize(list); i < count; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                View view = inflater.inflate(R.layout.item_tab_classify_content, null, false);
                TextView textView = view.findViewById(R.id.tv_tab_classify_content);
                textView.setText(list.get(i).getName());
                tab.setCustomView(view);
            }
        }

        TabLayout.Tab firstTab = mTabLayout.getTabAt(0);
        if (firstTab != null) {
            setSelectedTab(firstTab, true);
        }
    }

    private void setSelectedTab(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view != null) {
            view.findViewById(R.id.tv_tab_classify_content).setSelected(selected);
        }
    }
}
