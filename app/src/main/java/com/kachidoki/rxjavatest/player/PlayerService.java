package com.kachidoki.rxjavatest.player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
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
    public static final int CommandPlay =1;
    public static final int CommandNext =2;
    public static final int CommandClose =3;
    public static int PlayerNotification = 1;
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
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent==null){
            return super.onStartCommand(intent, flags, startId);
        }
        int command = intent.getIntExtra("command",0);
        sendPlayerNotification(command);
        setMediaPlayer(command);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(PlayerNotification);
    }

    private void sendPlayerNotification(int command){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Notification");
        builder.setContentText("自定义通知栏示例");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setShowWhen(false);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_custom_content);
        remoteViews.setTextViewText(R.id.title,"Notification");
        remoteViews.setTextViewText(R.id.text,"songname");
        if(command==CommandNext){
            remoteViews.setImageViewResource(R.id.btn1,R.drawable.ic_pause_white);
        }else if(command==CommandPlay){
            if(MusicPlayer.getPlayer().getMediaPlayer().isPlaying()){
                remoteViews.setImageViewResource(R.id.btn1,R.drawable.ic_pause_white);
            }else{
                remoteViews.setImageViewResource(R.id.btn1,R.drawable.ic_play_arrow_white_18dp);
            }
        }

        //bigView
        RemoteViews bigRemoteViews = new RemoteViews(getPackageName(),R.layout.notification_big_content);
        remoteViews.setTextViewText(R.id.title,"Notification");
        remoteViews.setTextViewText(R.id.text,"songname");
        if(command==CommandNext){
            remoteViews.setImageViewResource(R.id.btn1,R.drawable.ic_pause_white);
        }else if(command==CommandPlay){
            if(MusicPlayer.getPlayer().getMediaPlayer().isPlaying()){
                remoteViews.setImageViewResource(R.id.btn1,R.drawable.ic_pause_white);
            }else{
                remoteViews.setImageViewResource(R.id.btn1,R.drawable.ic_play_arrow_white_18dp);
            }
        }


        Intent Intent1 = new Intent(this,PlayerService.class);
        Intent1.putExtra("command",CommandPlay);

        PendingIntent PIntent1 =  PendingIntent.getService(this,5,Intent1,0);
        remoteViews.setOnClickPendingIntent(R.id.btn1,PIntent1);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn1,PIntent1);

        Intent Intent2 = new Intent(this,PlayerService.class);
        Intent2.putExtra("command",CommandNext);
        PendingIntent PIntent2 =  PendingIntent.getService(this,6,Intent2,0);
        remoteViews.setOnClickPendingIntent(R.id.btn2,PIntent2);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn2,PIntent2);

        Intent Intent3 = new Intent(this,PlayerService.class);
        Intent3.putExtra("command",CommandClose);
        PendingIntent PIntent3 =  PendingIntent.getService(this,7,Intent3,0);
        remoteViews.setOnClickPendingIntent(R.id.btn3,PIntent3);
        bigRemoteViews.setOnClickPendingIntent(R.id.btn3,PIntent3);

        builder.setCustomContentView(remoteViews);
        builder.setCustomBigContentView(bigRemoteViews);
        Notification notification = builder.build();
        notificationManager.notify(PlayerNotification,notification);
    }

    private void setMediaPlayer(int command){
        switch (command){
            case CommandNext:
                MusicPlayer.getPlayer().next();
                break;
        }
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
