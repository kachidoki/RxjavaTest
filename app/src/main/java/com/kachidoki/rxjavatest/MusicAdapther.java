package com.kachidoki.rxjavatest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kachidoki.rxjavatest.app.App;
import com.kachidoki.rxjavatest.bean.PlayEvent;
import com.kachidoki.rxjavatest.bean.SongList;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mayiwei on 16/11/20.
 */
public class MusicAdapther extends RecyclerView.Adapter {
    List<SongList> songLists;
    public static Context context;

    public MusicAdapther(Context context){
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicViewHolder musicViewHolder = (MusicViewHolder) holder;
        musicViewHolder.setData(songLists.get(position));
    }

    @Override
    public int getItemCount() {
        return songLists==null?0:songLists.size();
    }

    public void setData(List<SongList> songLists){
        this.songLists = songLists;
        notifyDataSetChanged();
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageIv) ImageView imageIv;
        @BindView(R.id.descriptionTv) TextView descriptionTv;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(final SongList songList){
            Glide.with(itemView.getContext()).load(songList.albumpic_big).into(imageIv);
            descriptionTv.setText(songList.songname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.playEvent.addSong(songList);
                    App.playEvent.setAction(PlayEvent.Action.PLAY);
                    EventBus.getDefault().post(App.playEvent);
                    context.startActivity(new Intent(context,MusicPlayAcitivity.class));
                }
            });
        }
    }
}
