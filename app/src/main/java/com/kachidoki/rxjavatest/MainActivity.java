package com.kachidoki.rxjavatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rxjavaSim) Button btn1;
    @BindView(R.id.rxjavaRetroit) Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.rxjavaSim)
    public void toRxSimple(){
        startActivity(new Intent(MainActivity.this,Elementary.class));
    }

    @OnClick(R.id.rxjavaRetroit)
    public void toRxRetrofit(){
        startActivity(new Intent(MainActivity.this,RetrofitTest.class));

    }
}
