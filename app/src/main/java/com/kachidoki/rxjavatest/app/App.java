package com.kachidoki.rxjavatest.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.kachidoki.rxjavatest.bean.PlayEvent;
import com.kachidoki.rxjavatest.player.PlayerService;

/**
 * Created by mayiwei on 16/11/20.
 */
public class App extends Application {
    public static Context sContext;
    public static int sScreenWidth;
    public static int sScreenHeight;
    public static PlayEvent playEvent;

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, PlayerService.class));

        playEvent = new PlayEvent();
        sContext = getApplicationContext();
        
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
    }

}
