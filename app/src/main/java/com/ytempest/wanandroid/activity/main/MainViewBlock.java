package com.ytempest.wanandroid.activity.main;

import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.block.ViewBlock;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author heqidu
 * @since 2020/6/21
 */
public class MainViewBlock extends ViewBlock {

    @BindView(R.id.navigationBar_main_content)
    BottomNavigationBar mNavigationBar;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, getHost());

        initNavigationBar();
    }

    /*Tabs*/

    private void initNavigationBar() {
        mNavigationBar.clearAll();
        for (MainTab tab : MainTab.values()) {
            mNavigationBar.addItem(new BottomNavigationItem(tab.iconSelected, tab.title)
                    .setInactiveIconResource(tab.iconNormal));
        }
        mNavigationBar.initialise();
    }
}
