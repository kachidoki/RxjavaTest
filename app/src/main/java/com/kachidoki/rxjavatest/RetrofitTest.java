package com.kachidoki.rxjavatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kachidoki.rxjavatest.bean.ApiResult;
import com.kachidoki.rxjavatest.bean.SongList;
import com.kachidoki.rxjavatest.network.NetWork;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mayiwei on 16/11/17.
 */
public class RetrofitTest extends AppCompatActivity {
    protected Subscription subscription;
    @BindView(R.id.RestdoSomething) Button doSomething;
    @BindView(R.id.gridRv) RecyclerView recyclerView;

    MusicAdapther musicAdapther = new MusicAdapther(RetrofitTest.this);

    Observer<List<SongList>> observer = new Observer<List<SongList>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(RetrofitTest.this, "数据加载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<SongList> songLists) {
            musicAdapther.setData(songLists);
        }
    };

    @OnClick(R.id.RestdoSomething)
    void doSomething() {
        unsubscribe();
        subscription = NetWork.getMusicApi()
                .getMusicList("27351", "afd32ae229f54eb88446af5f0bb5d52e", "5")
                .map(new Func1<ApiResult, List<SongList>>() {
                    @Override
                    public List<SongList> call(ApiResult apiResult) {
                        return apiResult.showapi_res_body.pagebean.songLists;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(musicAdapther);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
