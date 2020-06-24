package com.ytempest.wanandroid.activity.main.home;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.unit.IUnit;
import com.ytempest.wanandroid.base.unit.UnitFragment;

import java.util.List;

/**
 * @author heqidu
 * @since 2020/6/23
 */
public class HomeFrag extends UnitFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void onCreateUnits(List<IUnit> units) {
        units.add(new HomeViewUnit());
    }
}
