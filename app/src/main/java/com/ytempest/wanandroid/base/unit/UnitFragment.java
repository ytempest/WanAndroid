package com.ytempest.wanandroid.base.unit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytempest.wanandroid.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heqidu
 * @since 2020/6/24
 */
public abstract class UnitFragment extends BaseFragment {

    private final List<IUnit> mUnits;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void onCreateUnits(List<IUnit> units);

    public UnitFragment() {
        this.mUnits = new ArrayList<>();
        onCreateUnits(mUnits);
    }

    <U extends Unit> U getUnit(Class<U> clazz) {
        for (IUnit unit : mUnits) {
            if (clazz == unit.getClass()) {
                return (U) unit;
            }
        }
        throw new IllegalArgumentException("Unit didn't exist, whether you had added this unit: " + clazz.getCanonicalName());
    }


    /*Dispatch*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        for (IUnit unit : mUnits) {
            unit.onAttach(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (IUnit unit : mUnits) {
            unit.onCreate(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayoutId(), container, false);
        for (IUnit unit : mUnits) {
            unit.onCreateView(view, savedInstanceState);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (IUnit unit : mUnits) {
            unit.onViewCreated(view, savedInstanceState);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        for (IUnit unit : mUnits) {
            unit.onHiddenChanged(hidden);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (IUnit unit : mUnits) {
            unit.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (IUnit unit : mUnits) {
            unit.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (IUnit unit : mUnits) {
            unit.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (IUnit unit : mUnits) {
            unit.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        for (IUnit unit : mUnits) {
            unit.onDetach();
        }
    }

}
