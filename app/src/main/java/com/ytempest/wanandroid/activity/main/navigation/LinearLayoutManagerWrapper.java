package com.ytempest.wanandroid.activity.main.navigation;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.FloatRange;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * @author heqidu
 * @since 2020/12/29
 */
public class LinearLayoutManagerWrapper extends LinearLayoutManager {

    private static float MILLISECONDS_PER_INCH = 25F;  //修改可以改变数据,越大速度越慢

    public LinearLayoutManagerWrapper(Context context) {
        super(context);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return LinearLayoutManagerWrapper.this.computeScrollVectorForPosition(targetPosition);
            }

            //This returns the milliseconds it takes to
            //scroll one pixel.
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                //返回滑动一个pixel需要多少毫秒
                return MILLISECONDS_PER_INCH * ratio / displayMetrics.density;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    private float ratio = 1F;

    public void setSpeedRatio(@FloatRange(from = 0, to = 1) float ratio) {
        this.ratio = ratio;
    }

    public void resetSpeedRatio() {
        ratio = 1F;
    }
}
