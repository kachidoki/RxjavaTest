package com.kachidoki.rxjavatest.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayiwei on 16/11/12.
 */
public class PlayEvent {

    public enum Action {
        PLAY, STOP, RESUME, NEXT, PREVIOES, SEEK
    }

    private Action mAction;
    private SongList mSong;
    private List<SongList> mQueue;
    private int seekTo;

    public void addSong(SongList mSong){
        if (mQueue==null){
            mQueue = new ArrayList<SongList>();
        }
        setSong(mSong);
        mQueue.add(mSong);
    }
    public SongList getSong() {
        return mSong;
    }

    public void setSong(SongList song) {
        mSong = song;
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }

    public List<SongList> getQueue() {
        return mQueue;
    }

    public void setQueue(List<SongList> queue) {
        mQueue = queue;
    }

    public int getSeekTo() {
        return seekTo;
    }

    public void setSeekTo(int seekTo) {
        this.seekTo = seekTo;
    }
}