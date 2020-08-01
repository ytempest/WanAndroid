package com.ytempest.wanandroid.di.module;

import com.ytempest.wanandroid.activity.main.IMainContract;
import com.ytempest.wanandroid.activity.main.MainPresenter;
import com.ytempest.wanandroid.activity.main.home.HomePresenter;
import com.ytempest.wanandroid.activity.main.home.IHomeContract;
import com.ytempest.wanandroid.activity.main.knowledge.IKnowledgeContract;
import com.ytempest.wanandroid.activity.main.knowledge.KnowledgePresenter;
import com.ytempest.wanandroid.activity.main.navigation.INavigationContract;
import com.ytempest.wanandroid.activity.main.navigation.NavaigationPresenter;
import com.ytempest.wanandroid.activity.main.project.IProjectContract;
import com.ytempest.wanandroid.activity.main.project.ProjectPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author heqidu
 * @since 2020/7/4
 * 提供Presenter层的实现类
 */
@Module
public class PresenterModule {

    /*Activity*/

    @Provides
    IMainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }

    /*Fragment*/

    @Provides
    IHomeContract.Presenter provideHomePresenter() {
        return new HomePresenter();
    }

    @Provides
    IKnowledgeContract.Presenter provideKnowledgePresenter() {
        return new KnowledgePresenter();
    }

    @Provides
    INavigationContract.Presenter provideNavigationPresenter() {
        return new NavaigationPresenter();
    }

    @Provides
    IProjectContract.Presenter provideProjectPresenter() {
        return new ProjectPresenter();
    }
}
