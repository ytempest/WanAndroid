package com.ytempest.wanandroid.utils;

import android.annotation.SuppressLint;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;

/**
 * @author heqidu
 * @since 2020/12/18
 */
public class Utils {

    /**
     * 设置菜单栏显示ICON
     */
    @SuppressLint("RestrictedApi")
    public static void enableMenuShowIcon(Menu menu) {
        if (menu instanceof MenuBuilder) {
            try {
                MenuBuilder builder = (MenuBuilder) menu;
                builder.setOptionalIconsVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
