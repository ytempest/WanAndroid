package com.ytempest.wanandroid.download.state;

import android.app.Notification;

import com.ytempest.wanandroid.download.NotiClickAction;

/**
 * @author heqidu
 * @since 2020/10/28
 */
public class DownloadingState extends AbsDownloadState {

    DownloadingState() {
        super(NotiClickAction.ON_PAUSE_CLICK);
    }

    @Override
    protected void onStart(Object params) {
        super.onStart(params);
        Notification notification = mFactory.createStartNoti();
        mChannel.notifyNotification(notification);
        mChannel.resumeDownload();
    }

    private static final int MAX_PROGRESS = 100;
    private int mProgress;

    @Override
    public void onUpdateProgress(float percent) {
        super.onUpdateProgress(percent);
        int newProgress = (int) (percent * MAX_PROGRESS);
        if (mProgress == newProgress) return;
        mProgress = newProgress;
        Notification notification = mFactory.createDownloadingNoti(mProgress, MAX_PROGRESS);
        mChannel.notifyNotification(notification);
    }

    @Override
    protected void onClickAction() {
        moveTo(PauseState.class);
    }
}
