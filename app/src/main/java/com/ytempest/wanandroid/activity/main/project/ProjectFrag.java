package com.ytempest.wanandroid.activity.main.project;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;

/**
 * @author heqidu
 * @since 2020/6/23
 */
public class ProjectFrag extends MvpFragment<ProjectPresenter> implements IProjectContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_project;
    }

}
