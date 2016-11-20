package com.kachidoki.rxjavatest;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kachidoki.rxjavatest.app.App;
import com.kachidoki.rxjavatest.bean.PlayEvent;
import com.kachidoki.rxjavatest.bean.SongList;
import com.kachidoki.rxjavatest.player.MusicPlayer;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mayiwei on 16/11/20.
 */
public class MusicPlayAcitivity extends AppCompatActivity {


    @BindView(R.id.play) Button play;
    @BindView(R.id.pause) Button pause;
    @BindView(R.id.stop) Button stop;
    @BindView(R.id.MusicStatus) TextView MusicStatus;
    @BindView(R.id.MusicTime) TextView MusicTime;
    @BindView(R.id.MusicSeekBar) SeekBar MusicSeekBar;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    public Handler handler =new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if ( MusicPlayer.getPlayer().getMediaPlayer().isPlaying()){
                MusicStatus.setText("Playing");
                play.setText("Pause");
            }else {
                MusicStatus.setText("Pause");
                play.setText("Play");
            }

            if (MusicPlayer.getPlayer().getReady()){
                MusicTime.setText(time.format(MusicPlayer.getPlayer().getMediaPlayer().getCurrentPosition()) + "/"
                        + time.format(MusicPlayer.getPlayer().getMediaPlayer().getDuration()));
                MusicSeekBar.setMax(MusicPlayer.getPlayer().getMediaPlayer().getDuration());
                MusicSeekBar.setProgress(MusicPlayer.getPlayer().getMediaPlayer().getCurrentPosition());
            }


            handler.postDelayed(runnable, 100);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplay);
        ButterKnife.bind(this);
        MusicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MusicPlayer.getPlayer().getMediaPlayer().seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MusicPlayer.getPlayer().getMediaPlayer().isPlaying()) {
            MusicStatus.setText("Playing");
        } else {
            MusicStatus.setText("Pause");
        }

        if (MusicPlayer.getPlayer().getReady()){
            MusicSeekBar.setProgress(MusicPlayer.getPlayer().getMediaPlayer().getCurrentPosition());
            MusicSeekBar.setMax(MusicPlayer.getPlayer().getMediaPlayer().getDuration());
        }


        handler.post(runnable);
    }

    @OnClick(R.id.play)
    public void play() {
        if (App.playEvent.getQueue() == null) {
            App.playEvent.setQueue(new ArrayList<SongList>());
        }
        if (App.playEvent.getAction() == PlayEvent.Action.PLAY) {
            App.playEvent.setAction(PlayEvent.Action.STOP);
        } else {
            App.playEvent.setAction(PlayEvent.Action.PLAY);
        }
        EventBus.getDefault().post(App.playEvent);
    }


}
