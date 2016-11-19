package com.kachidoki.rxjavatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kachidoki.rxjavatest.bean.ApiResult;
import com.kachidoki.rxjavatest.network.NetWork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mayiwei on 16/11/17.
 */
public class RetrofitTest extends AppCompatActivity {
    protected Subscription subscription;
    @BindView(R.id.doSomething) Button doSomething;
    @BindView(R.id.image) ImageView image;
    final Observer<ApiResult> observer = new Observer<ApiResult>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(RetrofitTest.this, "数据加载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(ApiResult apiResult) {
            Log.e("Test", "songName: " + apiResult.showapi_res_body.pagebean.songLists.get(1).songname);
            Log.e("Test", "songerName: " + apiResult.showapi_res_body.pagebean.songLists.get(1).singername);
            Log.e("Test", "img: " + apiResult.showapi_res_body.pagebean.songLists.get(1).albumpic_small);
            Glide.with(RetrofitTest.this)
                    .load("http://ww3.sinaimg.cn/large/610dc034jw1f9tmhxq87lj20u011htae.jpg")
                    .into(image);
        }
    };


    @OnClick(R.id.doSomething)
    void doSomething() {
        unsubscribe();
        subscription = NetWork.getMusicApi()
                .getMusicList("27351", "afd32ae229f54eb88446af5f0bb5d52e", "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


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
