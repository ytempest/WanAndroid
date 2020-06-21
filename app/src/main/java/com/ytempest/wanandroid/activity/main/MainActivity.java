package com.ytempest.wanandroid.activity.main;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigationBar_main_content)
    BottomNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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