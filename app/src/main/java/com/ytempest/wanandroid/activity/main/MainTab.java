package com.ytempest.wanandroid.activity.main;

import android.support.annotation.IntDef;

import com.ytempest.wanandroid.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author heqidu
 * @since 2020/6/21
 */

public enum MainTab {

    HOME(R.mipmap.ic_tab_home, R.mipmap.ic_tab_home_selected, R.string.tab_home),
    KNOWLEDGE(R.mipmap.ic_tab_knowledge, R.mipmap.ic_tab_knowledge_selected, R.string.tab_knowledge),
    NAVIGATION(R.mipmap.ic_tab_navigation, R.mipmap.ic_tab_navigation_selected, R.string.tab_navigation),
    PROJECT(R.mipmap.ic_tab_project, R.mipmap.ic_tab_project_selected, R.string.tab_project),
    ;
    public int iconNormal;
    public int iconSelected;
    public int title;

    MainTab(int tabNormalIcon, int tabSelectIcon, int tabTitle) {
        this.iconNormal = tabNormalIcon;
        this.iconSelected = tabSelectIcon;
        this.title = tabTitle;
    }

    @IntDef({Idx.HOME, Idx.KNOWLEDGE, Idx.NAVIGATION, Idx.PROJECT,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Idx {
        int HOME = 0;
        int KNOWLEDGE = 1;
        int NAVIGATION = 2;
        int PROJECT = 3;

    }
}
