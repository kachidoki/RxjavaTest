package com.kachidoki.rxjavatest.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.kachidoki.rxjavatest.bean.SongList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mayiwei on 16/11/12.
 */
public class MusicPlayer implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener {

    private static MusicPlayer player = new MusicPlayer();



    private MediaPlayer mediaPlayer;
    private Context context;
    private List<SongList>  mQueue;
    private int mQueueIndex;
    private PlayMode playMode;



    private Boolean isReady;




    private enum PlayMode {
        LOOP, RANDOM, REPEAT
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    public static MusicPlayer getPlayer(){
        return player;
    }

    public static void setPlayer(MusicPlayer player){
        MusicPlayer.player = player;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MusicPlayer(){
        mediaPlayer = new ManagedMediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);

        mQueue = new ArrayList<>();
        mQueueIndex = 0;
        playMode = PlayMode.LOOP;
        isReady = false;
    }

    public void setQueue(List<SongList> queue,int index){
        mQueue = queue;
        mQueueIndex = index;
        play(getNowPlaying());
    }

    private void play(SongList song) {
        try {
            Log.e("Test","------Play-----");
            mediaPlayer.reset();
            mediaPlayer.setDataSource(song.downUrl);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void resume() {
        mediaPlayer.start();
    }

    public void next() {
        play(getNextSong());
    }

    public void previous() {
        play(getPreviousSong());
    }





    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        isReady = false;
        next();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        isReady = true;
        mediaPlayer.start();
    }




    private SongList getPreviousSong() {
        if (mQueue.isEmpty()) {
            return null;
        }
        switch (playMode) {
            case LOOP:
                return mQueue.get(getPreviousIndex());
            case RANDOM:
                return mQueue.get(getRandomIndex());
            case REPEAT:
                return mQueue.get(mQueueIndex);
        }
        return null;
    }

    public SongList getNowPlaying() {
        if (mQueue.isEmpty()) {
            return null;
        }
        return mQueue.get(mQueueIndex);
    }
    private SongList getNextSong() {
        if (mQueue.isEmpty()) {
            return null;
        }
        switch (playMode) {
            case LOOP:
                return mQueue.get(getNextIndex());
            case RANDOM:
                return mQueue.get(getRandomIndex());
            case REPEAT:
                return mQueue.get(mQueueIndex);
        }
        return null;
    }

    public int getCurrentPosition() {
        if (getNowPlaying() != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (getNowPlaying() != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }
    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        playMode = playMode;
    }

    private int getPreviousIndex() {
        mQueueIndex = (mQueueIndex - 1) % mQueue.size();
        return mQueueIndex;
    }

    private int getRandomIndex() {
        mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
        return mQueueIndex;
    }


    private int getNextIndex() {
        mQueueIndex = (mQueueIndex + 1) % mQueue.size();
        return mQueueIndex;
    }

    private void release() {
        mediaPlayer.release();
        mediaPlayer = null;
        context = null;
    }

}
