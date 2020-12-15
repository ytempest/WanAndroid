package com.ytempest.wanandroid.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author heqidu
 * @since 2020/12/15
 */
public class ImgLoader {
    public static void loadTo(ImageView view, String url) {
        Glide.with(view).load(url).into(view);
    }
}
