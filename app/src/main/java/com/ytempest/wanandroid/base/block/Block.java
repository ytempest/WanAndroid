package com.ytempest.wanandroid.base.block;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

/**
 * @author heqidu
 * @since 2020/6/21
 */
public class Block implements IBlock, GenericLifecycleObserver {

    private BlockActivity mHost;

    @Override
    public void onAttach(BlockActivity host) {
        this.mHost = host;
        mHost.getLifecycle().addObserver(this);
    }

    @Override
    public void onDetach() {
        mHost.getLifecycle().removeObserver(this);
        mHost = null;
    }

    protected BlockActivity getHost() {
        return mHost;
    }

    @NonNull
    protected <T extends Block> T getBlock(Class<T> clazz) {
        return mHost.getBlock(clazz);
    }

    /*Life*/

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                onCreate();
                break;
            case ON_START:
                onStart();
                break;
            case ON_RESUME:
                onResume();
                break;
            case ON_PAUSE:
                onPause();
                break;
            case ON_STOP:
                onStop();
                break;
            case ON_DESTROY:
                onDestroy();
                onDetach();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    /*Proxy*/

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    /*Delegate*/

    protected void setContentView(@LayoutRes int layoutResID) {
        getHost().setContentView(layoutResID);
    }

    protected <V extends View> V findViewById(@IdRes int id) {
        return getHost().findViewById(id);
    }

    protected Window getWindow() {
        return mHost.getWindow();
    }

    protected Intent getIntent() {
        return mHost.getIntent();
    }

    protected Resources getResources() {
        return mHost.getResources();
    }
}
