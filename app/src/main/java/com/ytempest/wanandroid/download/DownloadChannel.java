package com.ytempest.wanandroid.download;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;

import com.ytempest.tool.receiver.BaseReceiver;
import com.ytempest.tool.state.StateCtrl;
import com.ytempest.wanandroid.di.module.http.OkHttpManager;
import com.ytempest.wanandroid.download.state.AbsDownloadState;
import com.ytempest.wanandroid.download.state.DownloadingState;
import com.ytempest.wanandroid.download.state.FailState;
import com.ytempest.wanandroid.download.state.PrepareState;
import com.ytempest.wanandroid.download.state.SuccessState;

import java.io.File;

/**
 * @author heqidu
 * @since 2020/9/29
 */
public class DownloadChannel {

    private static final int NOTIFICATION_DOWNLOAD_ID = 11;

    private final Service mService;
    private final NotificationManagerCompat mCompat;
    private final DownloadUnit mDownloadUnit;
    private final BaseReceiver mReceiver;
    private final StateCtrl<AbsDownloadState> mStateCtrl;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    // TODO  heqidu:  
    private volatile long mLastDownloadSeek;

    public DownloadChannel(Service service) {
        mService = service;
        mCompat = NotificationManagerCompat.from(service);
        mDownloadUnit = new DownloadUnit(OkHttpManager.getInstance().getClient())
                .setDownloadListener(new DownloadUnit.DownloadListener() {

                    private static final int TIME_NOTI_DELAY = 300;

                    @Override
                    public void onProgress(float percent, long saveLen, long contentLength) {
                        mHandler.post(() -> {
                            mLastDownloadSeek = saveLen;
                            mStateCtrl.getCurrent().onUpdateProgress(percent);
                        });
                    }

                    @Override
                    public void onSuccess() {
                        // 延迟300毫秒，以确保该通知正常刷新
                        mHandler.postDelayed(() -> mStateCtrl.moveTo(SuccessState.class), TIME_NOTI_DELAY);
                        mLastDownloadSeek = 0;
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        mHandler.postDelayed(() -> mStateCtrl.moveTo(FailState.class), TIME_NOTI_DELAY);
                    }
                });

        // 通知点击广播
        IntentFilter filter = BaseReceiver.crateFilter(NotiClickAction.ON_PREPARE_CLICK,
                NotiClickAction.ON_SUCCESS_CLICK, NotiClickAction.ON_FAIL_CLICK,
                NotiClickAction.ON_CONTINUE_CLICK, NotiClickAction.ON_PAUSE_CLICK);
        mReceiver = new BaseReceiver(filter, false) {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                mStateCtrl.getCurrent().receiveClickAction(action);
            }
        };
        mReceiver.register(service);

        // 下载状态控制
        final NotificationFactory factory = new NotificationFactory(service);
        mStateCtrl = new StateCtrl<>(clazz -> {
            AbsDownloadState state = AbsDownloadState.create(clazz);
            state.setup(this, factory);
            return state;
        });
        mStateCtrl.start(PrepareState.class);
    }

    public void setup(String url, File file) {
        mLastDownloadSeek = 0;
        mDownloadUnit.setup(url, file);
    }

    public void startDownload() {
        mStateCtrl.moveTo(DownloadingState.class);
    }

    public void stopDownload() {
        mDownloadUnit.cancelDownload();
    }

    public void resumeDownload() {
        mDownloadUnit.startDownload(mLastDownloadSeek);
    }

    public void startForeground(Notification notification) {
        mService.startForeground(NOTIFICATION_DOWNLOAD_ID, notification);
    }

    public void notifyNotification(Notification notification) {
        mCompat.notify(NOTIFICATION_DOWNLOAD_ID, notification);
    }

    public void notifyNotificationDelay(Notification notification, long delay) {
        mHandler.postDelayed(() -> mCompat.notify(NOTIFICATION_DOWNLOAD_ID, notification), delay);
    }

    public void destroy() {
        mHandler.removeCallbacksAndMessages(null);
        mReceiver.unregister(mService);
    }
}
