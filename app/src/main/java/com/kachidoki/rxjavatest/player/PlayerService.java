package com.kachidoki.rxjavatest.player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;


import com.kachidoki.rxjavatest.MusicPlayAcitivity;
import com.kachidoki.rxjavatest.R;
import com.kachidoki.rxjavatest.bean.PlayEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by mayiwei on 16/11/12.
 */
public class PlayerService extends Service {

    private Notification notification;//通知栏
    private RemoteViews remoteViews;//通知栏布局
    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Test","-------PlayService Create------");
        EventBus.getDefault().register(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(PlayerService.this,0, new Intent(PlayerService.this, MusicPlayAcitivity.class), 0);
        remoteViews = new RemoteViews(getPackageName(), R.layout.play_notification);
        notification = new Notification(R.mipmap.ic_launcher,"歌曲正在播放", System.currentTimeMillis());
        notification.contentIntent = pendingIntent;
        notification.contentView = remoteViews;
        //标记位，设置通知栏一直存在
        notification.flags =Notification.FLAG_ONGOING_EVENT;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe
    public void onEvent(PlayEvent playEvent){
        switch (playEvent.getAction()) {
            case PLAY:
                MusicPlayer.getPlayer().setQueue(playEvent.getQueue(), 0);
                break;
            case NEXT:
                MusicPlayer.getPlayer().next();
                break;
            case STOP:
                MusicPlayer.getPlayer().pause();
        }
    }
}
