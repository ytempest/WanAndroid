package com.ytempest.wanandroid.activity.main.project.content;

import com.ytempest.wanandroid.base.presenter.IPresenter;
import com.ytempest.wanandroid.base.view.IView;
import com.ytempest.wanandroid.http.bean.ProjectClassifyBean;
import com.ytempest.wanandroid.http.bean.ProjectContentBean;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public interface IProjectContentContract {
    interface View extends IView {
        void displayProjectContent(ProjectContentBean projectContent);

        void onMoreProjectContentLoaded(ProjectContentBean projectContent);
    }

    interface Presenter extends IPresenter {
        void refreshContent(ProjectClassifyBean classifyBean);

        void loadMoreProjectContent(ProjectClassifyBean classifyBean);
    }
}
