package com.ytempest.wanandroid.activity.main.project.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ytempest.wanandroid.http.bean.ProjectClassifyBean;
import com.ytempest.wanandroid.utils.CoreFragPagerAdapter;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public class ProjectContentAdapter extends CoreFragPagerAdapter<ProjectClassifyBean> {

    public ProjectContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    protected Fragment onCreateFragment(ProjectClassifyBean data, int pos) {
        return ProjectContentFrag.newInstance(data);
    }

}
