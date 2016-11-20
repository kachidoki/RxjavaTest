package com.kachidoki.rxjavatest.player;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by mayiwei on 16/11/12.
 */
public class ManagedMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener{


    public enum Status{
        IDLE,INITIALIZED,STARED,PAUSED,STOPPED,COMLETED
    }

    private Status mState;

    private OnCompletionListener onCompletionListener;

    public ManagedMediaPlayer(){
        super();
        mState = Status.IDLE;
        super.setOnCompletionListener(this);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        super.setDataSource(path);
        mState = Status.INITIALIZED;
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        mState = Status.STARED;
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.onCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mState = Status.COMLETED;
        if (onCompletionListener!=null){
            onCompletionListener.onCompletion(mediaPlayer);
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = Status.STOPPED;
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = Status.PAUSED;
    }

    public Status getState(){
        return mState;
    }

    public boolean isComplete(){
        return mState == Status.COMLETED;
    }
}
