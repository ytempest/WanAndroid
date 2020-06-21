package com.ytempest.wanandroid.base.block;

import android.os.Bundle;

/**
 * @author heqidu
 * @since 2020/6/21
 */
public interface IBlock {
    void onAttach(BlockActivity activity);

    void onDetach();

    void onSaveInstanceState(Bundle outState);

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
